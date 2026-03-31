package com.smarthome.iot.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ItemSensorController {
    

    @GetMapping("item/sensor/{id}")
    public String getClientSensorPage(Model model, @PathVariable Long id){
        return "client/sensor/detail";
    }

}
