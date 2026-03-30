package com.smarthome.iot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotNull
    @NotEmpty(message = "Tên không được để trống")
    private String name;


    @NotNull
    @NotEmpty(message = "Loại cảm biến không được để trống")
    private String type;

    
    @NotNull
    @Min(value = 1, message = "Ngưỡng cảnh báo phải lớn hơn hoặc bằng 1")
    private double threshold;


    @NotNull
    @NotEmpty(message = "Trạng thái không được để trống")
    private String status;
    // roomId

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getThreshold() {
        return threshold;
    }
    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Sensor [id=" + id + ", name=" + name + ", type=" + type + ", threshold=" + threshold + ", status="
                + status + ", room=" + room + "]";
    }
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    
    
    
}
