package com.smarthome.iot.service;

import java.util.List;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Service;

import com.smarthome.iot.domain.Device;
import com.smarthome.iot.repository.DeviceRepository;

@Service
public class DeviceService {

    private final DaoAuthenticationProvider authProvider;
    private final DeviceRepository deviceRepository;
    private final MqttService mqttService;

    public DeviceService(DeviceRepository deviceRepository,
            DaoAuthenticationProvider authProvider,
            MqttService mqttService) {
        this.deviceRepository = deviceRepository;
        this.authProvider = authProvider;
        this.mqttService = mqttService;
    }

    public List<Device> getAllDevice() {
        return this.deviceRepository.findAll();
    }

    public Device createDevice(Device device) {
        return this.deviceRepository.save(device);
    }

    public Device findById(Long id) {
        return this.deviceRepository.findById(id).orElse(null);
    }

    public Device handleSaveDevice(Device device) {
        return this.deviceRepository.save(device);
    }

    public void deleteADevice(Long id) {
        this.deviceRepository.deleteById(id);
    }

    public long countDevice() {
        return this.deviceRepository.count();
    }

    public long countDeviceByStatus(String status) {
        return this.deviceRepository.countByStatus(status);
    }

    public List<Device> findByRoomId(Long roomId) {
        return this.deviceRepository.findByRoomId(roomId);
    }

    public Device toggleStatus(Long id) {
        Device device = this.deviceRepository.findById(id).orElse(null);
        if (device == null) {
            return null;
        }

        String newStatus = "ON".equals(device.getStatus()) ? "OFF" : "ON";
        device.setStatus(newStatus);
        Device saved = this.deviceRepository.save(device);

        // Publish command to broker for ESP32 relay control.
        this.mqttService.publishCommand(id, newStatus);

        return saved;
    }

    public void updateStatus(Long id, String status) {
        Device device = this.deviceRepository.findById(id).orElse(null);
        if (device != null) {
            device.setStatus(status);
            this.deviceRepository.save(device);
        }
    }
}