<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <jsp:include page="/WEB-INF/view/client/layout/header.jsp">
            <jsp:param name="title" value="${sensor.name} - Chi tiết - SmartHome IoT" />
        </jsp:include>

        <div class="container py-5 mt-5 mb-5">
            <div class="row align-items-center mb-5 pb-3 border-bottom">
                <div class="col-md-8">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="/client/sensor-list"
                                    class="text-muted text-decoration-none">Hệ thống cảm biến</a></li>
                            <li class="breadcrumb-item active text-primary fw-bold" aria-current="page">${sensor.name}
                            </li>
                        </ol>
                    </nav>
                    <h1 class="display-5 fw-bold text-dark m-0">
                        <i class="bi bi-cpu-fill text-success me-2"></i> ${sensor.name}
                    </h1>
                    <p class="text-muted mt-2 mb-0">Theo dõi lịch sử dữ liệu và trạng thái thiết bị.</p>
                </div>
                <div class="col-md-4 text-md-end mt-3 mt-md-0">
                    <a href="/client/sensor-list" class="btn btn-outline-secondary rounded-pill px-4">
                        <i class="bi bi-arrow-left me-2"></i> Quay lại
                    </a>
                </div>
            </div>

            <div class="row g-4">
                <!-- Sensor Info Card -->
                <div class="col-lg-4">
                    <div class="card border-0 shadow-sm rounded-4 overflow-hidden">
                        <div class="card-header bg-success text-white py-4 px-4 text-center">
                            <div class="icon-circle bg-white-transparent rounded-circle mx-auto mb-3 d-flex align-items-center justify-content-center"
                                style="width: 80px; height: 80px;">
                                <i class="bi bi-cpu-fill text-white fs-1"></i>
                            </div>
                            <h4 class="mb-0 fw-bold">Thông tin cảm biến</h4>
                        </div>
                        <div class="card-body p-4">
                            <div class="info-row mb-4 pb-3 border-bottom">
                                <span class="text-muted small d-block mb-1">Tên cảm biến</span>
                                <span class="h5 fw-bold text-dark">${sensor.name}</span>
                            </div>
                            <div class="info-row mb-4 pb-3 border-bottom">
                                <span class="text-muted small d-block mb-1">Phòng lắp đặt</span>
                                <span class="h5 fw-bold text-dark">
                                    <i class="bi bi-door-open-fill text-danger me-2"></i>
                                    <a href="/client/room/${sensor.room.id}"
                                        class="text-decoration-none text-dark">${sensor.room.name}</a>
                                </span>
                            </div>
                            <div class="info-row mb-4 pb-3 border-bottom">
                                <span class="text-muted small d-block mb-1">Loại tín hiệu</span>
                                <span class="badge bg-info-light text-info rounded-pill px-3 py-2">${sensor.type}</span>
                            </div>
                            <div class="info-row mb-4">
                                <span class="text-muted small d-block mb-1">Trạng thái hệ thống</span>
                                <span
                                    class="badge ${sensor.status == 'ACTIVE' ? 'bg-success' : 'bg-secondary'} rounded-pill px-3 py-2">
                                    ${sensor.status == 'ACTIVE' ? 'Đang hoạt động' : 'Đang tắt'}
                                </span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Data Table Card -->
                <div class="col-lg-8">
                    <div class="card border-0 shadow-sm rounded-4 overflow-hidden">
                        <div
                            class="card-header bg-white border-0 py-4 px-4 d-flex justify-content-between align-items-center">
                            <h4 class="mb-0 fw-bold">Lịch sử dữ liệu đo lường</h4>
                            <span class="badge bg-light text-muted rounded-pill px-3 py-2">${dataCount} bản ghi
                                gần nhất</span>
                        </div>
                        <div class="card-body p-0">
                            <c:if test="${empty dataList}">
                                <div class="text-center py-5">
                                    <i class="bi bi-clipboard-x fs-1 text-muted d-block mb-3"></i>
                                    <p class="text-muted">Chưa có dữ liệu nào được ghi nhận cho cảm biến này.</p>
                                </div>
                            </c:if>
                            <c:if test="${not empty dataList}">
                                <div class="table-responsive">
                                    <table class="table table-hover align-middle mb-0">
                                        <thead class="bg-light">
                                            <tr class="text-muted small text-uppercase">
                                                <th class="ps-4">Thứ tự</th>
                                                <th>Giá trị</th>
                                                <th class="text-end pe-4">Thời gian ghi nhận</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="data" items="${dataList}" varStatus="status">
                                                <tr>
                                                    <td class="ps-4 text-muted">#${status.index + 1}</td>
                                                    <td>
                                                        <span class="h4 fw-bold text-dark mb-0">
                                                            ${data.value}
                                                        </span>
                                                    </td>
                                                    <td class="text-end pe-4 text-muted">${data.formattedRecordedAt}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    
                    <!-- Alert Table Card -->
                    <div class="card border-0 shadow-sm rounded-4 mt-4 overflow-hidden">
                        <div class="card-header bg-danger text-white border-0 py-4 px-4 d-flex justify-content-between align-items-center">
                            <h4 class="mb-0 fw-bold"><i class="bi bi-exclamation-triangle-fill me-2"></i>Lịch sử cảnh báo</h4>
                            <span class="badge bg-white text-danger rounded-pill px-3 py-2">${alertCount} sự cố</span>
                        </div>
                        <div class="card-body p-0">
                            <c:if test="${empty alertList}">
                                <div class="text-center py-5">
                                    <i class="bi bi-shield-check fs-1 text-success d-block mb-3"></i>
                                    <p class="text-muted">Hệ thống an toàn. Chưa có sự cố nào được ghi nhận.</p>
                                </div>
                            </c:if>
                            <c:if test="${not empty alertList}">
                                <div class="table-responsive">
                                    <table class="table table-hover align-middle mb-0">
                                        <thead class="bg-light">
                                            <tr class="text-muted small text-uppercase">
                                                <th class="ps-4">Thông điệp</th>
                                                <th>Mức độ an toàn</th>
                                                <th class="text-end pe-4">Thời gian ghi nhận</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="alert" items="${alertList}">
                                                <tr>
                                                    <td class="ps-4 fw-bold text-dark">
                                                        <c:choose>
                                                            <c:when test="${alert.alert}">
                                                                <span class="text-danger"><i class="bi bi-exclamation-circle-fill me-2"></i>${alert.alertMessage}</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="text-success"><i class="bi bi-check-circle-fill me-2"></i>${alert.alertMessage}</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:if test="${alert.value != null}">
                                                            <span class="badge ${alert.alert ? 'bg-danger' : 'bg-success'} rounded-pill">
                                                                ${alert.value}
                                                            </span>
                                                        </c:if>
                                                        <c:if test="${alert.value == null}">---</c:if>
                                                    </td>
                                                    <td class="text-end pe-4 text-muted">${alert.formattedRecordedAt}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<style>
            .bg-white-transparent {
                background-color: rgba(255, 255, 255, 0.2);
            }

            .bg-info-light {
                background-color: #e7f1ff;
            }

            .pulse-badge {
                animation: pulse 2s infinite;
            }

            @keyframes pulse {
                0% {
                    transform: scale(1);
                }

                50% {
                    transform: scale(1.05);
                }

                100% {
                    transform: scale(1);
                }
            }
        </style>

<jsp:include page="/WEB-INF/view/client/layout/footer.jsp" />

