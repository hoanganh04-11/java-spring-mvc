package com.smarthome.iot.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smarthome.iot.domain.Room;
import com.smarthome.iot.domain.Sensor;
import com.smarthome.iot.service.RoomService;
import com.smarthome.iot.service.SensorService;

import jakarta.validation.Valid;


@Controller
public class SensorController {
    
    private final SensorService sensorService;
    private final RoomService roomService;

    public SensorController(SensorService sensorService,
                            RoomService roomService){
        this.sensorService = sensorService;
        this.roomService = roomService;
    }

    @GetMapping("/admin/sensor")
    public String getSensorPage(Model model){
        List<Sensor> sensors = this.sensorService.getAllSensor();
        model.addAttribute("sensors", sensors);
        return "admin/sensor/show";
    }

    @GetMapping("/admin/sensor/create")
    public String getSensorCreatePage(Model model) {
        model.addAttribute("newSensor", new Sensor());
        
        List<Room> rooms = this.roomService.getAllRoom();
        model.addAttribute("rooms", rooms);

        return "admin/sensor/create";
    }
    
    @PostMapping("/admin/sensor/create")
    public String postSensorCreate(Model model, 
                                    @ModelAttribute("newSensor") @Valid Sensor sensor, BindingResult newSensorBindingResult,
                                    @RequestParam("roomId") Long roomId){
        if(newSensorBindingResult.hasErrors()){
            return "/admin/sensor/create";
        }

        Room room = this.roomService.findById(roomId);
        sensor.setRoom(room);
        this.sensorService.createSensor(sensor);
        return "redirect:/admin/sensor/create";
    }


}
