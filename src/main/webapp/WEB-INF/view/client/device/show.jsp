<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/view/client/layout/header.jsp">
    <jsp:param name="title" value="Điều khiển thiết bị - SmartHome" />
</jsp:include>

<div class="container py-5 mt-5">
    <div class="text-center mx-auto mb-5" style="max-width: 760px;">
        <h1 class="display-5 fw-bold">Điều khiển thiết bị</h1>
        <p class="text-muted mb-0">Quản lý trạng thái bật/tắt thiết bị theo từng phòng.</p>
    </div>

    <c:if test="${empty devices}">
        <div class="alert alert-info rounded-4 shadow-sm border-0 p-4 text-center">
            Chưa có thiết bị nào trong hệ thống.
        </div>
    </c:if>

    <c:if test="${not empty devices}">
        <div class="mb-4 text-center">
            <div class="btn-group shadow-sm rounded-pill p-1 bg-white" role="group">
                <button class="btn btn-primary rounded-pill px-4 filter-btn active" type="button" onclick="filterRoom('all', this)">
                    Tất cả
                </button>
                <c:forEach var="room" items="${rooms}">
                    <button class="btn btn-white rounded-pill px-4 filter-btn text-muted" type="button" onclick="filterRoom('${room.id}', this)">
                        ${room.name}
                    </button>
                </c:forEach>
            </div>
        </div>

        <div class="row g-4" id="device-grid">
            <c:forEach var="device" items="${devices}">
                <div class="col-lg-4 col-md-6 device-col" data-room="${device.room != null ? device.room.id : 'none'}">
                    <div class="card border-0 shadow-sm rounded-4 h-100 device-card">
                        <div class="card-body p-4">
                            <div class="d-flex justify-content-between align-items-start mb-3">
                                <div>
                                    <h5 class="fw-bold text-dark mb-1">${device.name}</h5>
                                    <small class="text-muted text-uppercase">Thiết bị</small>
                                </div>
                                <button class="toggle-switch ${device.status == 'ON' ? 'on' : 'off'}"
                                        id="toggle-${device.id}"
                                        data-device-id="${device.id}"
                                        onclick="handleToggle(this)"></button>
                            </div>

                            <div class="d-flex justify-content-between align-items-center">
                                <span class="badge bg-light text-muted border rounded-pill px-3 py-2">
                                    ${device.room != null ? device.room.name : 'Chưa gán phòng'}
                                </span>
                                <span class="badge ${device.status == 'ON' ? 'bg-success' : 'bg-secondary'} rounded-pill px-3 py-2"
                                      id="status-${device.id}">
                                    ${device.status}
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>

<div class="position-fixed bottom-0 end-0 p-3" style="z-index:9999">
    <div id="toggleToast" class="toast align-items-center text-white border-0 shadow-lg" role="alert">
        <div class="d-flex">
            <div class="toast-body" id="toast-msg">Đã cập nhật trạng thái</div>
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
            if (!data.success) {
                alert('Lỗi: ' + (data.message || 'Không thể cập nhật thiết bị'));
                return;
            }

            const btn = document.getElementById('toggle-' + deviceId);
            const badge = document.getElementById('status-' + deviceId);

            if (data.status === 'ON') {
                btn.className = 'toggle-switch on';
                badge.className = 'badge bg-success rounded-pill px-3 py-2';
                badge.textContent = 'ON';
            } else {
                btn.className = 'toggle-switch off';
                badge.className = 'badge bg-secondary rounded-pill px-3 py-2';
                badge.textContent = 'OFF';
            }

            const toastEl = document.getElementById('toggleToast');
            document.getElementById('toast-msg').textContent = data.status === 'ON'
                ? 'Thiết bị đã bật'
                : 'Thiết bị đã tắt';
            toastEl.className = 'toast align-items-center text-white border-0 shadow-lg ' + (data.status === 'ON' ? 'bg-success' : 'bg-primary');
            new bootstrap.Toast(toastEl, { delay: 2500 }).show();
        })
        .catch(err => alert('Lỗi: ' + err));
    }

    function filterRoom(roomId, btn) {
        document.querySelectorAll('.filter-btn').forEach(b => {
            b.classList.remove('btn-primary', 'active');
            b.classList.add('btn-white', 'text-muted');
        });

        btn.classList.remove('btn-white', 'text-muted');
        btn.classList.add('btn-primary', 'active');

        document.querySelectorAll('.device-col').forEach(col => {
            col.style.display = (roomId === 'all' || col.dataset.room === roomId) ? '' : 'none';
        });
    }
</script>

<style>
    .device-card {
        transition: all 0.25s ease;
        border: 1px solid rgba(0, 0, 0, 0.05) !important;
    }

    .device-card:hover {
        transform: translateY(-6px);
        box-shadow: 0 1rem 2rem rgba(0, 0, 0, 0.08) !important;
    }

    .btn-white {
        background: #fff;
        border: 1px solid transparent;
    }
</style>

<jsp:include page="/WEB-INF/view/client/layout/footer.jsp" />
