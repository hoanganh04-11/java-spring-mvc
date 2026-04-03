package com.smarthome.iot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarthome.iot.domain.SensorData;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    

    //Lấy dữ liệu theo cảm biến (sensor), sắp xếp mới nhất trước
    List<SensorData> findBySensorIdOrderByRecordedAtDesc(Long sensorId);

    //Lấy N bản ghi mới nhất theo sensor
    List<SensorData> findTop10BySensorIdOrderByRecordedAtDesc(Long sensorId);

    //Tổng bản ghi theo cảm biến
    long countBySensorId(Long sensorId);
}
