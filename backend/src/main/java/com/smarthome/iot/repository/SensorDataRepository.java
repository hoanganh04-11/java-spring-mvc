package com.smarthome.iot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarthome.iot.domain.SensorData;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    

    //Lấy dữ liệu theo cảm biến, sắp xếp mới nhất trước
    List<SensorData> findBySensorIdOrderByRecordedAtDesc(Long sensorId);

    //Lấy 10 bản ghi mới nhất theo sensor
    List<SensorData> findTop10BySensorIdOrderByRecordedAtDesc(Long sensorId);

    //Tổng bản ghi theo cảm biến
    long countBySensorId(Long sensorId);

    // Lấy tất cả cảnh báo, mới nhất trước
    List<SensorData> findByIsAlertTrueOrderByRecordedAtDesc();

    // Lấy 10 cảnh báo gần nhất theo sensor, mới nhất trước
    List<SensorData> findTop10BySensorIdAndIsAlertTrueOrderByRecordedAtDesc(Long sensorId);

    // Lấy 10 bản ghi dữ liệu thường theo sensor
    List<SensorData> findTop10BySensorIdAndIsAlertFalseOrderByRecordedAtDesc(Long sensorId);
}
