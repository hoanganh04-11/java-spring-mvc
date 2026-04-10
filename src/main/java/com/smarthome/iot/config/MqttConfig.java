package com.smarthome.iot.config;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarthome.iot.domain.SensorData;
import com.smarthome.iot.service.DeviceService;
import com.smarthome.iot.service.SensorDataService;

@Configuration
@ConditionalOnProperty(name = "mqtt.enabled", havingValue = "true")
public class MqttConfig {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Value("${mqtt.broker.url:}")
    private String brokerUrl;

    @Value("${mqtt.client.id:}")
    private String clientId;

    @Value("${mqtt.username:}")
    private String username;

    @Value("${mqtt.password:}")
    private String password;

    @Value("${mqtt.topic.status:smarthome/device/+/status}")
    private String statusTopic;

    @Value("${mqtt.topic.sensor.data:smarthome/sensor/+/data}")
    private String sensorDataTopic;

    @Value("${mqtt.topic.sensor.alert:smarthome/sensor/+/alert}")
    private String sensorAlertTopic;

    @Bean
    public MqttClient mqttClient(ApplicationContext ctx) {
        if (brokerUrl == null || brokerUrl.isBlank() || clientId == null || clientId.isBlank()) {
            System.err.println(">>> MQTT WARNING: Missing mqtt.broker.url or mqtt.client.id");
            System.err.println(">>> MQTT: App will continue without MQTT client");
            return null;
        }

        try {
            MqttClient client = new MqttClient(brokerUrl, clientId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setAutomaticReconnect(true);
            options.setConnectionTimeout(5);
            options.setKeepAliveInterval(60);
            if (username != null && !username.isBlank()) {
                options.setUserName(username);
                options.setPassword(password != null ? password.toCharArray() : new char[0]);
            }

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.err
                            .println(">>> MQTT: Connection lost - " + (cause != null ? cause.getMessage() : "unknown"));
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    String payload = new String(message.getPayload(), StandardCharsets.UTF_8).trim();
                    System.out.println(">>> MQTT <- topic: " + topic + " | payload: " + payload);

                    try {
                        handleMessage(ctx, topic, payload);
                    } catch (Exception e) {
                        System.err.println(">>> MQTT: Handle message error - " + e.getMessage());
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // no-op
                }
            });

            client.connect(options);
            System.out.println(">>> MQTT: Connected to broker " + brokerUrl);

            Set<String> subscriptions = new LinkedHashSet<>();
            subscriptions.add(statusTopic);
            subscriptions.add(sensorDataTopic);
            subscriptions.add(sensorAlertTopic);

            for (String topic : subscriptions) {
                if (topic != null && !topic.isBlank()) {
                    client.subscribe(topic, 1);
                    System.out.println(">>> MQTT: Subscribe -> " + topic);
                }
            }

            return client;

        } catch (MqttException e) {
            System.err.println(">>> MQTT WARNING: Cannot connect broker " + brokerUrl + " - " + e.getMessage());
            System.err.println(">>> MQTT: App will continue without MQTT client");
            return null;
        }
    }

    private void handleMessage(ApplicationContext ctx, String topic, String payload) {
        String[] parts = topic.split("/");
        if (parts.length < 4 || !"smarthome".equals(parts[0])) {
            System.err.println(">>> MQTT: Unsupported topic format: " + topic);
            return;
        }

        if ("device".equals(parts[1])) {
            handleDeviceTopic(ctx, parts, payload, topic);
            return;
        }

        if ("sensor".equals(parts[1])) {
            handleSensorTopic(ctx, parts, payload, topic);
            return;
        }

        System.err.println(">>> MQTT: Unhandled topic: " + topic);
    }

    private void handleDeviceTopic(ApplicationContext ctx, String[] parts, String payload, String fullTopic) {
        if (parts.length < 4) {
            return;
        }

        Long deviceId = parseId(parts[2], "device");
        if (deviceId == null) {
            return;
        }

        DeviceService deviceService = ctx.getBean(DeviceService.class);

        if ("status".equals(parts[3])) {
            deviceService.updateStatus(deviceId, payload);
            return;
        }

        System.err.println(">>> MQTT: Unhandled device topic: " + fullTopic);
    }

    private void handleSensorTopic(ApplicationContext ctx, String[] parts, String payload, String fullTopic) {
        if (parts.length < 4) {
            return;
        }

        Long sensorId = parseId(parts[2], "sensor");
        if (sensorId == null) {
            return;
        }

        SensorDataService sensorDataService = ctx.getBean(SensorDataService.class);

        if ("data".equals(parts[3])) {
            parseSensorPayload(sensorId, payload, sensorDataService);
            return;
        }

        if ("alert".equals(parts[3])) {
            parseAlertPayload(sensorId, payload, sensorDataService);
            return;
        }

        System.err.println(">>> MQTT: Unhandled sensor topic: " + fullTopic);
    }

    private void parseAlertPayload(Long sensorId, String payload, SensorDataService service) {
        try {
            boolean isAlert = true;
            String message = "Cảnh báo vượt ngưỡng";
            Double value = null;

            if (payload.startsWith("{")) {
                Map<String, Object> data = OBJECT_MAPPER.readValue(payload, new TypeReference<Map<String, Object>>() {
                });
                Object msgObj = data.get("message");
                if (msgObj != null) {
                    message = msgObj.toString();
                }
                Object valueObj = data.get("value");
                if (valueObj != null) {
                    value = parseDouble(valueObj.toString());
                    if (value != null && value == 0.0) {
                        isAlert = false;
                        message = "An toàn";
                    }
                }
            } else {
                value = parseDouble(payload);
                if (value != null) {
                    if (value == 0.0) {
                        isAlert = false;
                        message = "Bình thường (Đã an toàn)";
                    } else if (value == 1.0) {
                        isAlert = true;
                        message = "Cảnh báo: Phát hiện sự cố!";
                    }
                }
            }

            SensorData saved = service.saveAlert(sensorId, isAlert, message, value);
            if (saved != null) {
                System.out.println(">>> MQTT: Alert saved - sensor=" + sensorId
                        + " isAlert=" + isAlert
                        + " message=" + message
                        + (value != null ? " value=" + value : ""));
            } else {
                System.err.println(">>> MQTT: Alert save failed - sensor " + sensorId + " not found");
            }
        } catch (Exception ex) {
            System.err.println(">>> MQTT: Alert parse error sensor=" + sensorId + " - " + ex.getMessage());
        }
    }

    private Long parseId(String text, String type) {
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException ex) {
            System.err.println(">>> MQTT: Invalid " + type + " id in topic: " + text);
            return null;
        }
    }

    private void parseSensorPayload(Long sensorId, String payload, SensorDataService service) {
        try {
            if (payload.startsWith("{")) {
                Map<String, Object> data = OBJECT_MAPPER.readValue(payload, new TypeReference<Map<String, Object>>() {
                });
                Object valueObj = data.get("value");
                if (valueObj == null) {
                    System.err.println(">>> MQTT: Sensor " + sensorId + " missing value field");
                    return;
                }

                Double value = parseDouble(valueObj.toString());
                if (value == null) {
                    System.err.println(">>> MQTT: Sensor " + sensorId + " invalid value field: " + valueObj);
                    return;
                }

                service.saveData(sensorId, value);
                System.out.println(">>> MQTT: Sensor " + sensorId + " -> value=" + value);
                return;
            }

            Double value = parseDouble(payload);
            if (value == null) {
                System.err.println(">>> MQTT: Sensor " + sensorId + " invalid numeric payload: " + payload);
                return;
            }

            service.saveData(sensorId, value);
            System.out.println(">>> MQTT: Sensor " + sensorId + " -> value=" + value);
        } catch (Exception ex) {
            System.err.println(">>> MQTT: Sensor " + sensorId + " parse payload error: " + ex.getMessage());
        }
    }

    private Double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
