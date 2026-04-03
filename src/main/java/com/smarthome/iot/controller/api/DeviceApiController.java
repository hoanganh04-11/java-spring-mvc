package com.smarthome.iot.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarthome.iot.domain.Device;
import com.smarthome.iot.service.DeviceService;
import com.smarthome.iot.service.MqttService;

/**
 * REST API cho web UI (AJAX từ trình duyệt).
 * KHÔNG dùng cho ESP32 — mọi giao tiếp với ESP32 đi qua MQTT.
 *
 * POST /api/v1/device/{id}/toggle
 * → Đổi trạng thái trong DB
 * → Publish MQTT command → ESP32 bật/tắt relay
 */
@RestController
@RequestMapping("/api/v1/device")
public class DeviceApiController {

    private final DeviceService deviceService;
    private final MqttService mqttService;

    public DeviceApiController(DeviceService deviceService, MqttService mqttService) {
        this.deviceService = deviceService;
        this.mqttService = mqttService;
    }

    /**
     * Toggle bật/tắt thiết bị từ web UI (AJAX).
     * POST /api/v1/device/{id}/toggle
     */
    @PostMapping("/{id}/toggle")
    public ResponseEntity<Map<String, Object>> toggleDevice(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Device device = this.deviceService.toggleStatus(id);
            if (device == null) {
                response.put("success", false);
                response.put("message", "Không tìm thấy thiết bị ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            response.put("success", true);
            response.put("id", device.getId());
            response.put("name", device.getName());
            response.put("status", device.getStatus());
            response.put("mqttConnected", this.mqttService.isConnected());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
