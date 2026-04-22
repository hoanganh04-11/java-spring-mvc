package com.smarthome.iot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarthome.iot.domain.Room;
import com.smarthome.iot.domain.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    
    Room findByName(String name);

    List<Sensor> findAll();

}
