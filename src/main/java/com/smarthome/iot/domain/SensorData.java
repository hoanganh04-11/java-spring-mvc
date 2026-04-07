package com.smarthome.iot.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "sensor_data")
public class SensorData {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    // Được Service set: true nếu value vượt ngưỡng của sensor
    @Transient
    private boolean aboveThreshold;

    @PrePersist
    public void prePersist() {
        this.recordedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public boolean isAboveThreshold() {
        return aboveThreshold;
    }

    public void setAboveThreshold(boolean aboveThreshold) {
        this.aboveThreshold = aboveThreshold;
    }

    @Override
    public String toString() {
        return "SensorData [id=" + id + ", value=" + value + ", recordedAt=" + recordedAt + "]";
    }


    
}
