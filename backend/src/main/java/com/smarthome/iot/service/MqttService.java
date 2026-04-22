package com.smarthome.iot.service;

import java.time.Instant;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttService {

    private MqttClient mqttClient;

    @Value("${mqtt.topic.command:smarthome/device/%d/command}")
    private String commandTopicTemplate;

    @Autowired(required = false)
    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public void publishCommand(Long deviceId, String command) {
        try {
            String topic = String.format(commandTopicTemplate, deviceId);
            publish(topic, command, 1, true);
        } catch (MqttException e) {
            System.err.println(">>> MQTT publish loi: " + e.getMessage());
        }
    }

    public String getCommandTopicTemplate() {
        return commandTopicTemplate;
    }

    public void publish(String topic, String payload, int qos, boolean retained) throws MqttException {
        if (mqttClient == null || !mqttClient.isConnected()) {
            System.err.println(">>> MQTT: Client chua ket noi - bo qua publish cho topic " + topic);
            return;
        }

        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        message.setRetained(retained);
        mqttClient.publish(topic, message);
        System.out.println(">>> MQTT publish -> topic: " + topic + " | payload: " + payload);
    }

    public boolean isConnected() {
        return mqttClient != null && mqttClient.isConnected();
    }
}
