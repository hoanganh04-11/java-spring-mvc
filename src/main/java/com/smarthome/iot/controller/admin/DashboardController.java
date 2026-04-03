package com.smarthome.iot.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smarthome.iot.service.DeviceService;
import com.smarthome.iot.service.RoomService;
import com.smarthome.iot.service.SensorService;
import com.smarthome.iot.service.UserService;


@Controller
public class DashboardController {
    
    private final UserService userService;
    private final DeviceService deviceService;
    private final RoomService roomService;
    private final SensorService sensorService;

    public DashboardController(DeviceService deviceService, 
                                RoomService roomService, 
                                SensorService sensorService, 
                                UserService userService){
        
        this.deviceService = deviceService;
        this.roomService = roomService;
        this.sensorService = sensorService;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getDashBoard(Model model){
        model.addAttribute("countRooms", this.roomService.countRoom());
        model.addAttribute("countDevices", this.deviceService.countDevice());
        model.addAttribute("countSensors", this.sensorService.countSensor());
        model.addAttribute("countDevicesOn", this.deviceService.countDeviceByStatus("ON"));
        model.addAttribute("countDevicesOff", this.deviceService.countDeviceByStatus("OFF"));
        model.addAttribute("countUsers", this.userService.countUser());
        return "admin/dashboard/show";
    }
}
