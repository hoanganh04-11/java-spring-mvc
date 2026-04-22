package com.smarthome.iot.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.smarthome.iot.domain.Device;
import com.smarthome.iot.domain.Room;
import com.smarthome.iot.service.DeviceService;
import com.smarthome.iot.service.RoomService;


@Controller
public class DeviceController {
    
    private final DeviceService deviceService;
    private final RoomService roomService;

    public DeviceController(DeviceService deviceService, RoomService roomService){
        this.deviceService = deviceService;
        this.roomService = roomService;
    }

    @GetMapping("/admin/device")
    public String getDevicePage(Model model){
        List<Device> devices = this.deviceService.getAllDevice();
        model.addAttribute("devices", devices);
        return "admin/device/show";
    }


    @GetMapping("/admin/device/create")
    public String getDeviceCreatePage(Model model){
        Device newDevice = new Device();
        newDevice.setRoom(new Room());
        model.addAttribute("newDevice", newDevice);

        List<Room> rooms = this.roomService.getAllRoom();
        model.addAttribute("rooms", rooms);

        return "admin/device/create";
    }

    @PostMapping("/admin/device/create")
    public String postDeviceCreate(Model model, @ModelAttribute("newDevice") Device device){
        if (device.getRoom() != null && device.getRoom().getId() != null) {
            device.setRoom(this.roomService.findById(device.getRoom().getId()));
        } else {
            device.setRoom(null);
        }
        this.deviceService.createDevice(device);
        return "redirect:/admin/device";
    }

    @GetMapping("/admin/device/{id}")
    public String getDeviceDetailPage(Model model, @PathVariable Long id){
        Device device = this.deviceService.findById(id);
        model.addAttribute("id", id);
        model.addAttribute("device", device);
        return "admin/device/detail";
    }

    @GetMapping("/admin/device/update/{id}")
    public String getDeviceUpdatePage(Model model, @PathVariable Long id){
        Device existingDevice = this.deviceService.findById(id);
        if (existingDevice == null) {
            return "redirect:/admin/device";
        }

        // Tạo object form riêng để tránh chỉnh trực tiếp entity đang được Hibernate quản lý
        Device currentDevice = new Device();
        currentDevice.setId(existingDevice.getId());
        currentDevice.setName(existingDevice.getName());
        currentDevice.setStatus(existingDevice.getStatus());
        currentDevice.setRoom(existingDevice.getRoom());

        List<Room> rooms = this.roomService.getAllRoom();
        model.addAttribute("rooms", rooms);
        model.addAttribute("newDevice", currentDevice);
        return "admin/device/update";
    }

    @PostMapping("/admin/device/update")
    public String postDeviceUpdate(Model model, @ModelAttribute("newDevice") Device device){
        Device currentDevice = this.deviceService.findById(device.getId());
        if(currentDevice != null){
            currentDevice.setName(device.getName());
            currentDevice.setStatus(device.getStatus());

            if (device.getRoom() != null && device.getRoom().getId() != null) {
                currentDevice.setRoom(this.roomService.findById(device.getRoom().getId()));
            } else {
                currentDevice.setRoom(null);
            }

            this.deviceService.handleSaveDevice(currentDevice);
        }

        return "redirect:/admin/device";
    }

    @GetMapping("/admin/device/delete/{id}")
    public String getDeleteDevicePage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("deleteDevice", new Device());
        return "admin/device/delete";
    }
    
    @PostMapping("/admin/device/delete")
    public String postDeleteDevice(Model model, @ModelAttribute("deleteDevice") Device device){
        this.deviceService.deleteADevice(device.getId());
        return "redirect:/admin/device";
    }
}
