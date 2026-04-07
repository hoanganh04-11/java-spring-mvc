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
     * Lưu dữ liệu từ ESP32.
     * ESP32 gửi sensorId, value, unit → Service tìm Sensor rồi lưu SensorData.
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

    /**
     * Lấy 10 bản ghi mới nhất của 1 sensor
     */
    public List<SensorData> getLatestData(Long sensorId) {
        return this.sensorDataRepository.findTop10BySensorIdOrderByRecordedAtDesc(sensorId);
    }

    /**
     * Lấy tất cả dữ liệu theo sensor
     */
    public List<SensorData> getAllDataBySensor(Long sensorId) {
        return this.sensorDataRepository.findBySensorIdOrderByRecordedAtDesc(sensorId);
    }
    /**
     * Lấy 10 bản ghi mới nhất và đánh dấu bản ghi nào vượt ngưỡng cảnh báo.
     * Logic nghiệp vụ này thuộc về Service, không để View tự tính.
     */
    public List<SensorData> getLatestDataWithThreshold(Long sensorId, double threshold) {
        List<SensorData> dataList = getLatestData(sensorId);
        dataList.forEach(d -> d.setAboveThreshold(d.getValue() != null && d.getValue() > threshold));
        return dataList;
    }


}
