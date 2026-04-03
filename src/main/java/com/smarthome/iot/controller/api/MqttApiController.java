package com.smarthome.iot.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smarthome.iot.service.MqttService;
import com.smarthome.iot.service.MqttTopicCatalogService;

@RestController
@RequestMapping("/api/v1/mqtt")
public class MqttApiController {

    private final MqttService mqttService;
    private final MqttTopicCatalogService mqttTopicCatalogService;

    public MqttApiController(MqttService mqttService, MqttTopicCatalogService mqttTopicCatalogService) {
        this.mqttService = mqttService;
        this.mqttTopicCatalogService = mqttTopicCatalogService;
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("connected", this.mqttService.isConnected());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/topics")
    public ResponseEntity<Map<String, Object>> topics() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("connected", this.mqttService.isConnected());
        response.put("cases", this.mqttTopicCatalogService.getCases());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/publish")
    public ResponseEntity<Map<String, Object>> publish(
            @RequestParam String topic,
            @RequestParam String payload,
            @RequestParam(defaultValue = "1") int qos,
            @RequestParam(defaultValue = "false") boolean retained) {
        Map<String, Object> response = new HashMap<>();

        if (!this.mqttService.isConnected()) {
            response.put("success", false);
            response.put("message", "MQTT client chua ket noi broker.");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }

        try {
            this.mqttService.publish(topic, payload, qos, retained);
            response.put("success", true);
            response.put("topic", topic);
            response.put("payload", payload);
            response.put("qos", qos);
            response.put("retained", retained);
            return ResponseEntity.ok(response);
        } catch (MqttException e) {
            response.put("success", false);
            response.put("message", "Publish MQTT that bai: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/device-command")
    public ResponseEntity<Map<String, Object>> publishDeviceCommand(
            @RequestParam Long deviceId,
            @RequestParam String command,
            @RequestParam(defaultValue = "false") boolean jsonPayload) {
        Map<String, Object> response = new HashMap<>();

        if (!this.mqttService.isConnected()) {
            response.put("success", false);
            response.put("message", "MQTT client chua ket noi broker.");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }

        if (jsonPayload) {
            this.mqttService.publishCommandJson(deviceId, command);
        } else {
            this.mqttService.publishCommand(deviceId, command);
        }
        response.put("success", true);
        response.put("deviceId", deviceId);
        response.put("command", command);
        response.put("jsonPayload", jsonPayload);
        return ResponseEntity.ok(response);
    }
}
