package com.smarthome.iot.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.smarthome.iot.domain.Room;
import com.smarthome.iot.service.RoomService;

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
        return "/admin/room/show";
    }

    @GetMapping("/admin/room/create")
    public String getRoomCreatePage(Model model){
        model.addAttribute("newRoom", new Room());
        return "/admin/room/create";
    }

    @PostMapping("/admin/room/create")
    public String postRoomCreate(Model model, @ModelAttribute("newRoom") Room room){
        this.roomService.createRoom(room);
        return "redirect:/admin/room";
    }
}
