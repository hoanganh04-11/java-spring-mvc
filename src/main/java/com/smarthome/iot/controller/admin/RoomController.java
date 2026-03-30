package com.smarthome.iot.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smarthome.iot.domain.Room;
import com.smarthome.iot.domain.Sensor;
import com.smarthome.iot.service.RoomService;
import com.smarthome.iot.service.SensorService;

@Controller
public class RoomController {
    
    private final RoomService roomService;

    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }

    @GetMapping("/admin/room")
    public String getRoomPage(Model model){
        List<Room> rooms = this.roomService.getAllRoom();
        model.addAttribute("rooms", rooms);
        return "admin/room/show";
    }
}
