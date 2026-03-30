package com.smarthome.iot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smarthome.iot.domain.Room;
import com.smarthome.iot.repository.RoomRepository;

@Service
public class RoomService {
    
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRoom(){
        return this.roomRepository.findAll();
    }

    public Room findById(Long id) {
        return this.roomRepository.findById(id).orElse(null);
    }
}
