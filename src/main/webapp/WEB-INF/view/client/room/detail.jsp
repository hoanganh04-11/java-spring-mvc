<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/view/client/layout/header.jsp">
    <jsp:param name="title" value="${room.name} - SmartHome IoT" />
</jsp:include>

<div class="container py-5 mt-5">
    <div class="row align-items-center mb-5 pb-3 border-bottom">
        <div class="col-md-8">
            <h1 class="display-5 fw-bold text-dark m-0">
                <i class="bi bi-door-open-fill text-danger me-2"></i> ${room.name}
            </h1>
        </div>
        <div class="col-md-4 text-md-end mt-3 mt-md-0">
            <a href="/client/room-list" class="btn btn-outline-secondary rounded-pill px-4">
                <i class="bi bi-arrow-left me-2"></i> Quay lai
            </a>
        </div>
    </div>

    <div class="mb-5">
        <div class="d-flex align-items-center justify-content-between mb-4">
            <h2 class="h4 fw-bold mb-0">
                <i class="bi bi-plug-fill text-primary me-2"></i> Thiet bi dieu khien
            </h2>
            <span class="badge bg-primary rounded-pill px-3 py-2">${devices != null ? devices.size() : 0} thiet bi</span>
        </div>

        <c:if test="${empty devices}">
            <div class="alert alert-light border-0 shadow-sm rounded-4 p-4 text-center">
                <i class="bi bi-info-circle fs-1 text-muted mb-3 d-block"></i>
                <p class="text-muted mb-0">Phong nay hien chua duoc lap dat thiet bi dieu khien nao.</p>
            </div>
        </c:if>

        <c:if test="${not empty devices}">
            <div class="row g-4">
                <c:forEach var="device" items="${devices}">
                    <div class="col-md-6 col-lg-4">
                        <div class="card border-0 shadow-sm rounded-4 device-card h-100">
                            <div class="card-body p-4 d-flex align-items-center justify-content-between">
                                <div class="d-flex align-items-center gap-3">
                                    <div class="icon-circle bg-light rounded-circle d-flex align-items-center justify-content-center"
                                        style="width: 50px; height: 50px;">
                                        <i class="bi bi-lamp-fill text-primary fs-3"></i>
                                    </div>
                                    <div>
                                        <h6 class="mb-1 fw-bold text-dark">${device.name}</h6>
                                        <span class="badge ${device.status == 'ON' ? 'bg-success' : 'bg-secondary'} rounded-pill"
                                            id="room-status-${device.id}">
                                            ${device.status}
                                        </span>
                                    </div>
                                </div>
                                <button class="toggle-switch ${device.status == 'ON' ? 'on' : 'off'}"
                                    id="room-toggle-${device.id}" data-device-id="${device.id}"
                                    onclick="handleToggle(this)">
                                </button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>

    <div>
        <div class="d-flex align-items-center justify-content-between mb-4">
            <h2 class="h4 fw-bold mb-0">
                <i class="bi bi-thermometer-half text-success me-2"></i> Thong so cam bien
            </h2>
        </div>

        <c:if test="${room.sensors == null || room.sensors.size() == 0}">
            <div class="alert alert-light border-0 shadow-sm rounded-4 p-4 text-center">
                <i class="bi bi-cpu fs-1 text-muted mb-3 d-block"></i>
                <p class="text-muted mb-0">Phong nay chua co cac mo-dun cam bien do luong.</p>
            </div>
        </c:if>

        <c:if test="${room.sensors != null && room.sensors.size() > 0}">
            <div class="row g-4">
                <c:forEach var="sensor" items="${room.sensors}">
                    <div class="col-lg-6">
                        <div class="card border-0 shadow-sm rounded-4 h-100">
                            <div class="card-header bg-white border-0 pt-4 px-4 d-flex justify-content-between align-items-center">
                                <h5 class="mb-0 fw-bold">
                                    <i class="bi bi-cpu text-success me-2"></i> ${sensor.name}
                                </h5>
                                <span class="badge ${sensor.status == 'ACTIVE' ? 'bg-success-light text-success' : 'bg-light text-muted'} rounded-pill px-3 py-2">
                                    ${sensor.status == 'ACTIVE' ? 'Hoat dong' : 'Dang tat'}
                                </span>
                            </div>
                            <div class="card-body p-4">
                                <div class="row mb-4">
                                    <div class="col-6">
                                        <p class="text-muted small mb-1">Loai cam bien</p>
                                        <span class="badge bg-info-light text-info rounded-pill px-3">${sensor.type}</span>
                                    </div>
                                    <div class="col-6 text-end">
                                        <p class="text-muted small mb-1">Nguong bao dong</p>
                                        <p class="fw-bold mb-0 text-dark">${sensor.threshold != null ? sensor.threshold : '---'}</p>
                                    </div>
                                </div>

                                <h6 class="fw-bold text-dark border-bottom pb-2 mb-3">Du lieu moi nhat</h6>
                                <c:choose>
                                    <c:when test="${empty sensor.latestData}">
                                        <div class="text-center py-3 bg-light rounded-4">
                                            <small class="text-muted fst-italic">Chua co du lieu tu phan cung</small>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="table-responsive">
                                            <table class="table table-hover align-middle mb-0">
                                                <thead class="table-light">
                                                    <tr class="small text-muted">
                                                        <th>Gia tri</th>
                                                        <th class="text-end">Thoi gian ghi nhan</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="data" items="${sensor.latestData}" end="4">
                                                        <tr>
                                                            <td class="fw-bold text-dark fs-5">
                                                                ${data.value}
                                                                <c:if test="${sensor.threshold != null && data.value > sensor.threshold}">
                                                                    <span class="badge bg-danger ms-1" title="Vuot nguong">!</span>
                                                                </c:if>
                                                            </td>
                                                            <td class="text-end text-muted small">${data.recordedAt}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="card-footer bg-white border-0 p-4 pt-0">
                                <a href="/client/sensor/${sensor.id}" class="btn btn-sm btn-outline-success rounded-pill px-4">
                                    <i class="bi bi-graph-up me-2"></i> Xem chi tiet bieu do
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>

<div class="position-fixed bottom-0 end-0 p-3" style="z-index:9999">
    <div id="toggleToast" class="toast align-items-center text-white border-0" role="alert">
        <div class="d-flex">
            <div class="toast-body" id="toast-msg">Da cap nhat</div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>
    </div>
</div>
<script>
    function handleToggle(btn) {
        const deviceId = btn.getAttribute('data-device-id');
        toggleDevice(deviceId);
    }

    function toggleDevice(deviceId) {
        fetch('/client/device/' + deviceId + '/toggle', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                '${_csrf.headerName}': '${_csrf.token}'
            }
        })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    const btn = document.getElementById('room-toggle-' + deviceId);
                    const badge = document.getElementById('room-status-' + deviceId);
                    if (data.status === 'ON') {
                        btn.className = 'toggle-switch on';
                        badge.className = 'badge bg-success rounded-pill';
                        badge.textContent = 'ON';
                    } else {
                        btn.className = 'toggle-switch off';
                        badge.className = 'badge bg-secondary rounded-pill';
                        badge.textContent = 'OFF';
                    }
                    const toastEl = document.getElementById('toggleToast');
                    document.getElementById('toast-msg').textContent =
                        data.status === 'ON' ? 'Da bat - lenh gui qua MQTT' : 'Da tat - lenh gui qua MQTT';
                    toastEl.className = 'toast align-items-center text-white border-0 '
                        + (data.status === 'ON' ? 'bg-success' : 'bg-secondary');
                    new bootstrap.Toast(toastEl, { delay: 3000 }).show();
                } else {
                    alert('Loi: ' + data.message);
                }
            })
            .catch(err => alert('Loi: ' + err));
    }
</script>

<style>
    .bg-success-light {
        background-color: #e9f7ef;
    }

    .bg-info-light {
        background-color: #e7f1ff;
    }

    .device-card {
        transition: transform 0.2s;
    }

    .device-card:hover {
        transform: scale(1.02);
    }
</style>

<jsp:include page="/WEB-INF/view/client/layout/footer.jsp" />

