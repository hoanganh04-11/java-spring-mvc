package com.smarthome.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarthome.iot.domain.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    long countByStatus(String status);
} 
