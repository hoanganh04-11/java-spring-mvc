package com.smarthome.iot.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "admin/room/show";
    }

    @GetMapping("/admin/room/create")
    public String getRoomCreatePage(Model model){
        model.addAttribute("newRoom", new Room());
        return "admin/room/create";
    }

    @PostMapping("/admin/room/create")
    public String postRoomCreate(Model model, @ModelAttribute("newRoom") Room room){
        this.roomService.createRoom(room);
        return "redirect:/admin/room";
    }

    @GetMapping("/admin/room/{id}")
    public String getRoomDetailPage(Model model, @PathVariable Long id){
        Room room = this.roomService.findById(id);
        model.addAttribute("id", id);
        model.addAttribute("room", room);
        return "admin/room/detail";
    }

    @GetMapping("/admin/room/update/{id}")
    public String getRoomUpdatePage(Model model, @PathVariable Long id){
        Room currentRoom = this.roomService.findById(id);
        model.addAttribute("newRoom", currentRoom);
        return "admin/room/update";
    }

    @PostMapping("/admin/room/update")
    public String postUpdateRoom(Model model, @ModelAttribute("newRoom") Room room){
        Room currentRoom = this.roomService.findById(room.getId());
        if(currentRoom != null){
            currentRoom.setName(room.getName());


            this.roomService.handleSaveRoom(currentRoom);
        }
        return "redirect:/admin/room";
    }
    
    @GetMapping("/admin/room/delete/{id}")
    public String getDeleteRoomPage(Model model, @PathVariable Long id){
        model.addAttribute("id", id);

        model.addAttribute("deleteRoom", new Room());
        return "admin/room/delete";
    }

    @PostMapping("admin/room/delete")
    public String postDeleteRoom(Model model, @ModelAttribute("deleteRoom") Room room){
        this.roomService.deleteARoom(room.getId());
        return "redirect:admin/room";
    }
}
