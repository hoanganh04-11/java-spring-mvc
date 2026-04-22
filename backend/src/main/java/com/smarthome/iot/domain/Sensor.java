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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @NotEmpty(message = "Tên không được để trống")
    private String name;


    @NotNull
    @NotEmpty(message = "Loại cảm biến không được để trống")
    private String type;

    @NotNull
    @NotEmpty(message = "Trạng thái không được để trống")
    private String status;
    // roomId

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Transient
    private List<SensorData> latestData;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }




    public List<SensorData> getLatestData() {
        return latestData;
    }


    public void setLatestData(List<SensorData> latestData) {
        this.latestData = latestData;
    }


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Sensor [id=" + id + ", name=" + name + ", type=" + type + ", status="
                + status + ", room=" + room + "]";
    }
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    
    
    
}
