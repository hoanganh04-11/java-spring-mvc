package com.smarthome.iot.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
            @ModelAttribute("newSensor") @Valid Sensor sensor,
            BindingResult newSensorBindingResult) {

        List<Room> rooms = this.roomService.getAllRoom();
        model.addAttribute("rooms", rooms);

        if (newSensorBindingResult.hasErrors()) {
            return "admin/sensor/create";
        }

        Long roomId = sensor.getRoom().getId();
        sensor.setRoom(this.roomService.findById(roomId));

        this.sensorService.createSensor(sensor);

        return "redirect:/admin/sensor";
    }

    @GetMapping("/admin/sensor/{id}")
    public String getSensorDetailPage(Model model, @PathVariable Long id){
        Sensor sensor = this.sensorService.findById(id);
        model.addAttribute("id", id);
        model.addAttribute("sensor", sensor);
        return "admin/sensor/detail";
    }

    @GetMapping("/admin/sensor/update/{id}")
    public String getUpdateSensorPage(Model model, @PathVariable Long id){
        List<Room> rooms = this.roomService.getAllRoom();
        model.addAttribute("rooms", rooms);

        Sensor currentSensor = this.sensorService.findById(id);
        model.addAttribute("newSensor", currentSensor);
        return "admin/sensor/update";
    }

    @PostMapping("/admin/sensor/update")
    public String postUpdateSensor(Model model, @ModelAttribute("newSensor") Sensor sensor){
        Sensor currentSensor = this.sensorService.findById(sensor.getId());

        if(currentSensor != null){
            currentSensor.setName(sensor.getName());
            currentSensor.setType(sensor.getType());
            currentSensor.setStatus(sensor.getStatus());

            List<Room> rooms = this.roomService.getAllRoom();
            model.addAttribute("rooms", rooms);

            if (sensor.getRoom() != null && sensor.getRoom().getId() != null) {
                currentSensor.setRoom(this.roomService.findById(sensor.getRoom().getId()));
            } else {
                currentSensor.setRoom(null);
            }
            this.sensorService.handleSaveSensor(currentSensor);
        }
        return "redirect:/admin/sensor";
    }

    @GetMapping("/admin/sensor/delete/{id}")
    public String getDeleteSensorPage(Model model, @PathVariable Long id){
        model.addAttribute("id", id);
        model.addAttribute("deleteSensor", new Sensor());
        return "admin/sensor/delete";
    }

    @PostMapping("/admin/sensor/delete")
    public String postDeleteUser(Model model, @ModelAttribute("deleteSensor") Sensor sensor){
        this.sensorService.deleteASensor(sensor.getId());
        return "redirect:/admin/sensor";
    }
}
