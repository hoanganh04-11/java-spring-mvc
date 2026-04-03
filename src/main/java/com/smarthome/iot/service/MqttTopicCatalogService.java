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

    @Value("${mqtt.topic.device.availability:smarthome/device/+/availability}")
    private String availabilityTopic;

    @Value("${mqtt.topic.device.command.ack:smarthome/device/+/command/ack}")
    private String commandAckTopic;

    @Value("${mqtt.topic.device.command.error:smarthome/device/+/command/error}")
    private String commandErrorTopic;

    @Value("${mqtt.topic.device.telemetry:smarthome/device/+/telemetry}")
    private String deviceTelemetryTopic;

    @Value("${mqtt.topic.sensor.data:smarthome/sensor/+/data}")
    private String sensorDataTopic;

    @Value("${mqtt.topic.sensor.alert:smarthome/sensor/+/alert}")
    private String sensorAlertTopic;

    @Value("${mqtt.topic.system.heartbeat:smarthome/system/+/heartbeat}")
    private String systemHeartbeatTopic;

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
                        "Device availability",
                        "esp32->server",
                        availabilityTopic,
                        "smarthome/device/101/availability",
                        "\"online\" or \"offline\"",
                        "LWT/heartbeat availability de theo doi online-offline",
                        1,
                        true),
                new MqttTopicCase(
                        "Command acknowledge",
                        "esp32->server",
                        commandAckTopic,
                        "smarthome/device/101/command/ack",
                        "{\"command\":\"ON\",\"result\":\"accepted\"}",
                        "ESP32 xac nhan da nhan lenh",
                        1,
                        false),
                new MqttTopicCase(
                        "Command error",
                        "esp32->server",
                        commandErrorTopic,
                        "smarthome/device/101/command/error",
                        "{\"command\":\"ON\",\"error\":\"relay timeout\"}",
                        "Bao loi khi thiet bi khong thuc thi duoc lenh",
                        1,
                        false),
                new MqttTopicCase(
                        "Device telemetry",
                        "esp32->server",
                        deviceTelemetryTopic,
                        "smarthome/device/101/telemetry",
                        "{\"rssi\":-61,\"heap\":185324}",
                        "So lieu suc khoe node ESP32",
                        0,
                        false),
                new MqttTopicCase(
                        "Sensor data",
                        "esp32->server",
                        sensorDataTopic,
                        "smarthome/sensor/201/data",
                        "{\"value\":28.5,\"unit\":\"C\"} or \"28.5\"",
                        "So lieu cam bien dinh ky",
                        1,
                        false),
                new MqttTopicCase(
                        "Sensor alert",
                        "esp32->server",
                        sensorAlertTopic,
                        "smarthome/sensor/201/alert",
                        "{\"value\":70,\"level\":\"high\",\"threshold\":60}",
                        "Canh bao vuot nguong",
                        1,
                        false),
                new MqttTopicCase(
                        "System heartbeat",
                        "esp32->server",
                        systemHeartbeatTopic,
                        "smarthome/system/node-01/heartbeat",
                        "{\"uptime\":5321,\"fw\":\"1.0.4\"}",
                        "Nhip song theo chu ky cua node",
                        0,
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
