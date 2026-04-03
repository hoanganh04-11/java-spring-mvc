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

    public DeviceService(DeviceRepository deviceRepository, DaoAuthenticationProvider authProvider){
        this.deviceRepository = deviceRepository;
        this.authProvider = authProvider;
    }


    public List<Device> getAllDevice(){
        return this.deviceRepository.findAll();
    }

    public Device createDevice(Device device){
        return this.deviceRepository.save(device);
    }

    public Device findById(Long id){
        return this.deviceRepository.findById(id).orElse(null);
    }

    public Device handleSaveDevice(Device device){
        Device newDevice = this.deviceRepository.save(device);
        return newDevice;
    }

    public void deleteADevice(Long id){
        this.deviceRepository.deleteById(id);
    }

    public long countDevice(){
        return this.deviceRepository.count();
    }

    public long countDeviceByStatus(String status){
        return this.deviceRepository.countByStatus(status);
    }

    public List<Device> findByRoomId(Long roomId) {
        return this.deviceRepository.findByRoomId(roomId);
    }

    /**
     * Toggle trạng thái ON/OFF của thiết bị.
     * Sau khi đổi trạng thái trong DB, publish lệnh qua MQTT để ESP32 thực thi.
     */
    public Device toggleStatus(Long id) {
        Device device = this.deviceRepository.findById(id).orElse(null);
        if (device == null)
            return null;

        String newStatus = "ON".equals(device.getStatus()) ? "OFF" : "ON";
        device.setStatus(newStatus);
        Device saved = this.deviceRepository.save(device);

        // Publish lệnh qua MQTT broker → ESP32 nhận và bật/tắt relay
        // this.mqttService.publishCommand(id, newStatus);

        return saved;
    }

    /**
     * Cập nhật trạng thái từ phản hồi MQTT của ESP32.
     */
    public void updateStatus(Long id, String status) {
        Device device = this.deviceRepository.findById(id).orElse(null);
        if (device != null) {
            device.setStatus(status);
            this.deviceRepository.save(device);
        }
    }
}
