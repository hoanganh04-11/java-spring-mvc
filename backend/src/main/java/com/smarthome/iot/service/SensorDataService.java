package com.smarthome.iot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smarthome.iot.domain.Sensor;
import com.smarthome.iot.domain.SensorData;
import com.smarthome.iot.repository.SensorDataRepository;
import com.smarthome.iot.repository.SensorRepository;

@Service
public class SensorDataService {
    
    private final SensorDataRepository sensorDataRepository;
    private final SensorRepository sensorRepository;

    public SensorDataService(SensorDataRepository sensorDataRepository, 
                             SensorRepository sensorRepository){
            
            this.sensorDataRepository = sensorDataRepository;
            this.sensorRepository = sensorRepository;
    }

    /**
     * Lưu dữ liệu từ ESP32 gửi sensorId, value → Service tìm Sensor rồi lưu SensorData.
     */
    public SensorData saveData(Long sensorId, Double value) {
        Sensor sensor = this.sensorRepository.findById(sensorId).orElse(null);
        if (sensor == null) {
            return null;
        }

        SensorData data = new SensorData();
        data.setSensor(sensor);
        data.setValue(value);

        return this.sensorDataRepository.save(data);
    }

    // Lấy 10 bản ghi mới nhất của 1 sensor
    
    public List<SensorData> getLatestData(Long sensorId) {
        return this.sensorDataRepository.findTop10BySensorIdOrderByRecordedAtDesc(sensorId);
    }

    
    // Lấy 10 bản ghi đo lường thông thường của 1 sensor
    
    public List<SensorData> getTop10NormalDataBySensor(Long sensorId) {
        return this.sensorDataRepository.findTop10BySensorIdAndIsAlertFalseOrderByRecordedAtDesc(sensorId);
    }

    // Lấy tất cả dữ liệu theo sensor
    public List<SensorData> getAllDataBySensor(Long sensorId) {
        return this.sensorDataRepository.findBySensorIdOrderByRecordedAtDesc(sensorId);
    }

    
    // Lưu bản tin cảnh báo vượt ngưỡng từ ESP32, Tái sử dụng bảng sensor_data với cờ isAlert.
    
    public SensorData saveAlert(Long sensorId, boolean isAlert, String message, Double value) {
        Sensor sensor = this.sensorRepository.findById(sensorId).orElse(null);
        if (sensor == null) {
            return null;
        }

        SensorData data = new SensorData();
        data.setSensor(sensor);
        data.setValue(value);
        data.setAlert(isAlert);
        data.setAlertMessage(message);

        return this.sensorDataRepository.save(data);
    }

    // Lấy tất cả cảnh báo, mới nhất trước
    public List<SensorData> getAllAlerts() {
        return this.sensorDataRepository.findByIsAlertTrueOrderByRecordedAtDesc();
    }

    // Lấy 10 cảnh báo gần nhất theo cảm biến
    public List<SensorData> getAlertsBySensor(Long sensorId) {
        return this.sensorDataRepository.findTop10BySensorIdAndIsAlertTrueOrderByRecordedAtDesc(sensorId);
    }
}
