# HomeSmartIoT

He thong nha thong minh xay dung bang Spring Boot + JSP + MySQL, ho tro quan ly phong/cam bien/thiet bi, phan quyen nguoi dung, va tich hop MQTT de nhan du lieu va dieu khien thiet bi theo thoi gian thuc.

## Tong quan

Du an hien tai gom 2 phan chinh:

- `Admin`: Quan tri du lieu he thong (user, room, sensor, device, dashboard).
- `Client`: Giao dien nguoi dung de xem thong tin phong/cam bien/thiet bi, va dieu khien thiet bi neu da dang nhap.

Ngoai ra, du an co cac REST API de test MQTT va dieu khien thiet bi qua endpoint API.

## Cong nghe dang dung

- Java 17
- Spring Boot 3.5.12
- Spring MVC + JSP (Tomcat Jasper + JSTL)
- Spring Data JPA (Hibernate)
- Spring Security
- Spring Session JDBC
- MySQL
- MQTT client: Eclipse Paho (`org.eclipse.paho.client.mqttv3`)

## Cau truc thu muc chinh

```text
src/main/java/com/smarthome/iot
|- config/                 # Security, MQTT, MVC config
|- controller/
|  |- admin/               # Controller trang quan tri
|  |- client/              # Controller trang nguoi dung
|  |- api/                 # REST API (MQTT, device)
|- domain/                 # Entity JPA: User, Role, Room, Sensor, Device, SensorData, Alert
|- repository/             # JPA repository
|- service/                # Business logic

src/main/webapp/WEB-INF/view
|- admin/                  # JSP giao dien admin
|- client/                 # JSP giao dien client

src/main/resources
|- application.properties
```

## Tinh nang hien co

### Admin

- Dang nhap vao khu vuc `/admin` voi role `ADMIN`
- CRUD User: `/admin/user/**`
- CRUD Room: `/admin/room/**`
- CRUD Sensor: `/admin/sensor/**`
- CRUD Device: `/admin/device/**`
- Dashboard thong ke tong quan: `/admin`

### Client

- Trang chu: `/`
- Dang ky: `/register`
- Dang nhap: `/login`
- Danh sach phong: `/client/room-list`
- Chi tiet phong: `/client/room/{id}`
- Danh sach cam bien: `/client/sensor-list`
- Chi tiet cam bien: `/client/sensor/{id}`
- Danh sach thiet bi: `/client/device`

### Phan quyen client/guest

- Guest co the xem du lieu (`/client/**`).
- Guest KHONG duoc dieu khien thiet bi.
- API toggle thiet bi chi cho `USER`/`ADMIN`:
  - `POST /client/device/{id}/toggle`

## MQTT trong du an

Du an da co day du luong MQTT:

- Cau hinh client va subscribe topic trong `MqttConfig`
- Publish command trong `MqttService`
- API test MQTT trong `MqttApiController`
- Toggle device co publish MQTT command trong `DeviceService.toggleStatus(...)`

### Cac endpoint API MQTT

- `GET /api/v1/mqtt/status` - Kiem tra trang thai ket noi MQTT
- `GET /api/v1/mqtt/topics` - Danh sach topic case mau
- `POST /api/v1/mqtt/publish` - Publish topic/payload tuy y
- `POST /api/v1/mqtt/device-command` - Publish lenh dieu khien theo `deviceId`

### Device API

- `POST /api/v1/device/{id}/toggle`

## Cau hinh `application.properties`

File: `src/main/resources/application.properties`

Ban can kiem tra cac gia tri sau truoc khi chay:

- MySQL
  - `spring.datasource.url=jdbc:mysql://localhost:3306/smarthome`
  - `spring.datasource.username=...`
  - `spring.datasource.password=...`

- MQTT
  - `mqtt.enabled=true`
  - `mqtt.broker.url=` **(bat buoc phai dien broker, neu de trong thi MQTT khong ket noi)**
  - `mqtt.client.id=test`

Vi du broker local:

```properties
mqtt.broker.url=tcp://localhost:1883
```

## Khoi tao database

Tao DB truoc khi chay:

```sql
CREATE DATABASE smarthome CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

`spring.jpa.hibernate.ddl-auto=update` se tu tao/cap nhat schema theo entity.

Luu y role:

- Dang ky nguoi dung moi gan role `USER` bang code (`getRoleByName("USER")`).
- Can dam bao bang `roles` da co ban ghi `USER` va `ADMIN`.

## Huong dan chay du an

### 1) Clone

```bash
git clone <repo-url>
cd iot
```

### 2) Build

```bash
mvnw.cmd -DskipTests compile
```

### 3) Run

```bash
mvnw.cmd spring-boot:run
```

### 4) Truy cap

- Client: `http://localhost:8080/`
- Login: `http://localhost:8080/login`
- Register: `http://localhost:8080/register`
- Admin: `http://localhost:8080/admin`

## Kiem tra nhanh MQTT sau khi chay

1. Kiem tra ket noi:

```text
GET /api/v1/mqtt/status
```

2. Publish test:

```text
POST /api/v1/mqtt/device-command?deviceId=1&command=ON
```

3. Theo doi log server de thay thong diep `MQTT Connected`, `Subscribe`, `Publish`.

## Luu y hien tai

- Neu `mqtt.broker.url` de trong, app van chay binh thuong nhung bo qua ket noi MQTT.
- Session dang dung `spring-session-jdbc`, app se tao bang session khi khoi dong (`spring.session.jdbc.initialize-schema=always`).

## De xuat mo rong tiep

- Them migration SQL (Flyway/Liquibase) thay cho `ddl-auto=update`.
- Bo sung seed data role `USER/ADMIN` tu dong khi khoi dong.
- Them test integration cho MQTT va security.
- Them dashboard realtime cho client.