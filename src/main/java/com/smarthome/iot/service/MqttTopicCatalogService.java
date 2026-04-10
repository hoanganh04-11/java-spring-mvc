package com.smarthome.iot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttTopicCatalogService {

    @Value("${mqtt.topic.command:smarthome/device/%d/command}")
    private String commandTopicTemplate;

    @Value("${mqtt.topic.status:smarthome/device/+/status}")
    private String statusTopic;

    @Value("${mqtt.topic.sensor.data:smarthome/sensor/+/data}")
    private String sensorDataTopic;

    @Value("${mqtt.topic.sensor.alert:smarthome/sensor/+/alert}")
    private String sensorAlertTopic;

    public List<MqttTopicCase> getCases() {
        return List.of(
                new MqttTopicCase(
                        "Device command",
                        "server->esp32",
                        commandTopicTemplate,
                        String.format(commandTopicTemplate, 101L),
                        "\"ON\" or \"OFF\" or {\"command\":\"ON\",\"source\":\"server\"}",
                        "Server gui lenh dieu khien relay/thiet bi",
                        1,
                        true),
                new MqttTopicCase(
                        "Device status feedback",
                        "esp32->server",
                        statusTopic,
                        "smarthome/device/101/status",
                        "\"ON\" or \"OFF\"",
                        "Trang thai thuc te sau khi ESP32 xu ly lenh",
                        1,
                        true),
                new MqttTopicCase(
                        "Sensor data",
                        "esp32->server",
                        sensorDataTopic,
                        "smarthome/sensor/201/data",
                        "{\"value\":28.5} or \"28.5\"",
                        "So lieu cam bien dinh ky",
                        1,
                        false),
                new MqttTopicCase(
                        "Sensor alert",
                        "esp32->server",
                        sensorAlertTopic,
                        "smarthome/sensor/201/alert",
                        "{\"message\":\"Nhiệt độ quá mức\"} or 1 (Alert) or 0 (Safe)",
                        "Canh bao vuot nguong",
                        1,
                        false));
    }

    public record MqttTopicCase(
            String caseName,
            String direction,
            String topicPattern,
            String exampleTopic,
            String payloadExample,
            String description,
            int qos,
            boolean retained) {
    }
}
