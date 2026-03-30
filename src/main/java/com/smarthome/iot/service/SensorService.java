package com.smarthome.iot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smarthome.iot.domain.Sensor;
import com.smarthome.iot.repository.SensorRepository;


@Service
public class SensorService {
    
    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository){
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> getAllSensor(){
        return this.sensorRepository.findAll();
    }

    public Sensor createSensor(Sensor sensor){
        return this.sensorRepository.save(sensor);
    }

    
}
