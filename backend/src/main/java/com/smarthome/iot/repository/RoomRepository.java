package com.smarthome.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarthome.iot.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
