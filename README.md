# 🏠 HomeSmartIoT

> Hệ thống nhà thông minh sử dụng **Spring Boot + MySQL + MQTT + ESP32/STM32/LoRa Gateway** để giám sát cảm biến và điều khiển thiết bị theo thời gian thực.

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?logo=java" alt="Java 17"/>
  <img src="https://img.shields.io/badge/Spring_Boot-3.5.12-green?logo=springboot" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql" alt="MySQL 8"/>
  <img src="https://img.shields.io/badge/MQTT-Eclipse%20Paho-purple" alt="MQTT Paho"/>
  <img src="https://img.shields.io/badge/JSP-JSTL-ff69b4" alt="JSP JSTL"/>
  <img src="https://img.shields.io/badge/license-MIT-brightgreen" alt="License"/>
</p>

---

## 📋 Mục lục

- [Giới thiệu](#-giới-thiệu)
- [Tính năng chính](#-tính-năng-chính)
- [Kiến trúc hệ thống](#-kiến-trúc-hệ-thống)
- [Công nghệ sử dụng](#-công-nghệ-sử-dụng)
- [Yêu cầu hệ thống](#️-yêu-cầu-hệ-thống)
- [Cài đặt và chạy dự án](#-cài-đặt-và-chạy-dự-án)
- [Thiết lập cơ sở dữ liệu](#-thiết-lập-cơ-sở-dữ-liệu)
- [Cấu trúc dự án](#-cấu-trúc-dự-án)
- [Chức năng phần cứng](#-chức-năng-phần-cứng)
- [Định hướng phát triển](#-định-hướng-phát-triển)
- [Hướng dẫn đóng góp](#-hướng-dẫn-đóng-góp)
- [Quy ước code](#-quy-ước-code)
- [Thành viên nhóm](#-thành-viên-nhóm)
- [FAQ](#-faq)

---

## 📖 Giới thiệu

**HomeSmartIoT** là dự án xây dựng hệ thống nhà thông minh cho phép:

- Quản lý phòng trong nhà
- Theo dõi dữ liệu cảm biến theo thời gian thực
- Điều khiển thiết bị từ giao diện web
- Cảnh báo khi giá trị cảm biến vượt ngưỡng an toàn
- Kết nối phần cứng và backend qua **MQTT** (ESP32/LoRa Gateway)

Dự án hiện tại tập trung vào nền tảng web:

- **Spring Boot + Spring MVC + JSP** cho backend và giao diện
- **MySQL** lưu trữ dữ liệu hệ thống
- **MQTT (Eclipse Paho)** để publish/subscribe dữ liệu thiết bị
- Hỗ trợ phân quyền **Guest / User / Admin**

---

## ✨ Tính năng chính

| Module | Mô tả |
|---|---|
| 🔐 **Đăng nhập / Đăng ký** | Xác thực và tạo tài khoản người dùng |
| 👤 **Phân quyền** | `ADMIN` quản trị, `USER` sử dụng, `Guest` chỉ xem |
| 🏠 **Quản lý phòng** | CRUD phòng trong admin, hiển thị danh sách/chi tiết ở client |
| 🌡️ **Quản lý cảm biến** | Theo dõi cảm biến theo phòng, xem dữ liệu mới nhất |
| 💡 **Quản lý thiết bị** | CRUD thiết bị và gán phòng trong admin |
| 🎛️ **Điều khiển thiết bị** | Bật/tắt thiết bị từ client (chỉ User/Admin) |
| 📊 **Admin Dashboard** | Thống kê tổng quan users/rooms/sensors/devices |
| 📡 **MQTT API** | Kiểm tra trạng thái MQTT, publish test topic/command |

---

## 📡 Kiến trúc hệ thống

```text
[Cảm biến / Node IoT (STM32)]
        │
        ├── LoRa
        ▼
[ESP32 Gateway]
        │ (MQTT)
        ▼
[Spring Boot Server]
        │
        ├── JPA/Hibernate
        ▼
[MySQL Database]
        │
        ▼
[Web UI: Admin + Client]
```

### Luồng hoạt động

1. Node IoT thu thập dữ liệu cảm biến và gửi về gateway.
2. Gateway publish dữ liệu lên broker MQTT.
3. Backend subscribe topic MQTT, parse payload và lưu DB.
4. Website hiển thị dữ liệu phòng/cảm biến/thiết bị.
5. Khi người dùng điều khiển thiết bị, backend cập nhật trạng thái và publish lệnh MQTT ngược lại thiết bị.

---

## 📦 Công nghệ sử dụng

| Công nghệ | Vai trò |
|---|---|
| **Java 17** | Ngôn ngữ backend |
| **Spring Boot 3.5.12** | Nền tảng ứng dụng |
| **Spring MVC** | Controller + View |
| **Spring Security** | Xác thực và phân quyền |
| **Spring Session JDBC** | Quản lý session trong DB |
| **Spring Data JPA / Hibernate** | ORM dữ liệu |
| **MySQL 8** | Cơ sở dữ liệu |
| **JSP / JSTL / Bootstrap** | Giao diện web |
| **Eclipse Paho MQTT** | MQTT client (publish/subscribe) |
| **Git & GitHub** | Quản lý mã nguồn |

---

## ⚙️ Yêu cầu hệ thống

| Thành phần | Phiên bản khuyến nghị |
|---|---|
| Java JDK | **17** |
| Maven | **3.8+** |
| MySQL | **8.0+** |
| IDE | VS Code / IntelliJ IDEA / STS |
| MQTT Broker | Mosquitto / EMQX / HiveMQ |

---

## 🚀 Cài đặt và chạy dự án

### 1. Clone project

```bash
git clone https://github.com/your-username/HomeSmartIoT.git
cd HomeSmartIoT
```

### 2. Cấu hình `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smarthome
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

mqtt.enabled=true
mqtt.broker.url=tcp://localhost:1883
mqtt.client.id=test
mqtt.username=
mqtt.password=
```

> Lưu ý: nếu `mqtt.broker.url` để trống, app vẫn chạy nhưng MQTT sẽ không kết nối.

### 3. Chạy project

Nếu đã cài Maven:

```bash
mvn spring-boot:run
```

Nếu dùng Maven Wrapper:

```bash
mvnw.cmd spring-boot:run
```

### 4. Truy cập ứng dụng

```text
http://localhost:8080
```

- Client: `/`
- Login: `/login`
- Register: `/register`
- Admin: `/admin`

---

## 🗄️ Thiết lập cơ sở dữ liệu

### Tạo database

```sql
CREATE DATABASE smarthome CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Một số bảng chính

- `users`
- `roles`
- `rooms`
- `sensors`
- `sensor_data`
- `devices`
- `alerts`
- `spring_session`, `spring_session_attributes`

### Quan hệ dữ liệu cơ bản

```text
roles 1 --- n users
rooms 1 --- n sensors
rooms 1 --- n devices
sensors 1 --- n sensor_data
sensors 1 --- n alerts
```

---

## 📁 Cấu trúc dự án

```text
HomeSmartIoT/
├── src/
│   ├── main/
│   │   ├── java/com/smarthome/iot/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   │   ├── admin/
│   │   │   │   ├── client/
│   │   │   │ 
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── domain/
│   │   │   └── IotApplication.java
│   │   ├── resources/
│   │   │   └── application.properties
│   │   └── webapp/WEB-INF/view/
│   │       ├── admin/
│   │       └── client/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## 🔌 Chức năng phần cứng

> Phần firmware hiện không nằm trong repo này, nhưng backend đã sẵn sàng để tích hợp.

### Node cảm biến (STM32)

- Đọc dữ liệu cảm biến (nhiệt độ/độ ẩm/khí gas/ánh sáng...)
- Truyền dữ liệu theo khung qua Gateway Esp32 bằng LoRa

### Gateway (ESP32)

- Nhận dữ liệu từ node qua LoRa
- Chuyển tiếp lên MQTT broker
- Nhận lệnh điều khiển từ topic command và truyền ngược về node

---

## 📈 Định hướng phát triển

- Thêm biểu đồ realtime cho sensor data
- Bổ sung notify qua Email/Telegram khi vượt ngưỡng
- Tách profile cấu hình `dev/staging/prod`
- Seed dữ liệu role mặc định (`USER`, `ADMIN`) tự động
- Viết test integration cho MQTT + Security

---

## 🤝 Hướng dẫn đóng góp

### Quy trình làm việc với Git

```bash
git checkout main
git pull origin main
git checkout -b feature/ten-tinh-nang
```

Sau khi code xong:

```bash
git add .
git commit -m "feat: thêm chức năng quản lý phòng"
git push origin feature/ten-tinh-nang
```

Sau đó tạo **Pull Request** lên nhánh `main`.

> Không nên push trực tiếp vào `main` khi làm việc nhóm.

---

## 📝 Quy ước code

### Commit message

Sử dụng format:

```text
<type>: <mô tả ngắn>
```

### Ví dụ

```text
feat: thêm chức năng quản lý thiết bị
fix: sửa lỗi phân quyền guest điều khiển thiết bị
docs: cập nhật README
refactor: tách service xử lý MQTT
```

### Quy ước đặt tên

| Thành phần | Quy ước | Ví dụ |
|---|---|---|
| Class | `PascalCase` | `UserController` |
| Method / Variable | `camelCase` | `handleLogin()` |
| Constant | `UPPER_SNAKE_CASE` | `MAX_SENSOR_VALUE` |
| Package | `lowercase` | `com.smarthome.iot` |
| Git branch | `kebab-case` | `feature/device-control` |

---

## 👥 Thành viên nhóm

| Họ tên | Mã Sinh Viên | Vai trò |
|---|---|---|
| Nguyễn Tiến Quân | B22DCVT425 | Trưởng nhóm |
| Hoàng Trung Anh | B22DCVT017 | Thành viên |
| Nguyễn Khánh Nam | B22DCVT361 | Thành viên |


---

## 🖼️ Ảnh giao diện

```md
## 🖼️ Giao diện trang chủ

![Image 1](src/main/webapp/resources/client/img/homepage.png)
```

```md
## 🖼️ Giao diện admin

![Image 1](src/main/webapp/resources/client/img/admin.png)

```

---

## ❓ FAQ

<details>
  <summary><b>Lỗi port 8080 đã được sử dụng?</b></summary>

Kiểm tra process đang chiếm cổng:

```bash
netstat -ano | findstr :8080
```

Sau đó tắt process:

```bash
taskkill /PID <PID> /F
```

</details>

<details>
  <summary><b>Lỗi "mvn is not recognized"?</b></summary>

Bạn chưa cài Maven hoặc chưa thêm Maven vào `PATH`.

Có thể dùng luôn Maven Wrapper:

```bash
mvnw.cmd spring-boot:run
```

</details>

<details>
  <summary><b>Lỗi không kết nối được MySQL?</b></summary>

Kiểm tra lại:

- MySQL đã bật chưa
- Tên database đã đúng chưa
- Username/password đã đúng chưa
- Cổng MySQL có phải `3306` không

</details>

<details>
  <summary><b>Mqtt status báo disconnected?</b></summary>

Kiểm tra:

- `mqtt.enabled=true`
- `mqtt.broker.url` đã điền chưa (ví dụ `tcp://localhost:1883`)
- Broker MQTT có đang chạy không
- Firewall/network có chặn cổng broker không

Test nhanh:

```text
GET /api/v1/mqtt/status
```

</details>

---

## 📄 License

Dự án này được phát hành theo giấy phép **MIT**.

---

<p align="center">
  Made with ❤️ by <strong>HomeSmartIoT Team</strong>
</p>
