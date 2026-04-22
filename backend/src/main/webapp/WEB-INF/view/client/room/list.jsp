<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <jsp:include page="/WEB-INF/view/client/layout/header.jsp">
            <jsp:param name="title" value="Danh sách phòng - SmartHome IoT" />
        </jsp:include>

        <div class="container py-5 mt-5">
            <div class="text-center mx-auto mb-5" style="max-width: 700px;">
                <h1 class="display-4 fw-bold">Danh sách phòng</h1>
                <p class="text-muted">Lựa chọn phòng để xem chi tiết các thông số cảm biến và điều khiển thiết bị liên
                    quan.</p>
            </div>

            <c:if test="${empty rooms}">
                <div class="alert alert-info rounded-4 shadow-sm border-0 p-4">
                    <div class="d-flex align-items-center gap-3">
                        <i class="bi bi-info-circle-fill fs-2"></i>
                        <div>
                            <h5 class="mb-1 fw-bold">Chưa có phòng nào</h5>
                            <p class="mb-0">Hệ thống hiện chưa ghi nhận phòng nào. Vui lòng liên hệ quản trị viên.</p>
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="row g-4">
                <c:forEach var="room" items="${rooms}">
                    <div class="col-md-6 col-lg-4">
                        <a href="/client/room/${room.id}" class="text-decoration-none">
                            <div class="card border-0 shadow-sm rounded-4 overflow-hidden h-100 room-card">
                                <div class="card-body text-center p-4">
                                    <div class="bg-light rounded-circle mx-auto mb-3 d-flex align-items-center justify-content-center"
                                        style="width: 80px; height: 80px;">
                                        <i class="bi bi-door-open-fill text-danger fs-1"></i>
                                    </div>
                                    <h4 class="fw-bold text-dark mb-3">${room.name}</h4>

                                    <div class="d-flex justify-content-center gap-2 mt-2">
                                        <c:choose>
                                            <c:when test="${not empty room.sensors}">
                                                <span
                                                    class="badge bg-success-light text-success rounded-pill px-3 py-2">
                                                    <i class="bi bi-cpu me-1"></i> ${room.sensors.size()} cảm biến
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-light text-muted rounded-pill px-3 py-2">Trống cảm
                                                    biến</span>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${not empty room.devices}">
                                                <span
                                                    class="badge bg-primary-light text-primary rounded-pill px-3 py-2">
                                                    <i class="bi bi-plug me-1"></i> ${room.devices.size()} thiết bị
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-light text-muted rounded-pill px-3 py-2">Trống
                                                    thiết bị</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="card-footer bg-white border-0 text-center pb-4 pt-0">
                                    <span class="text-primary fw-bold">Chi tiết <i
                                            class="bi bi-chevron-right ms-1"></i></span>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        <style>
            .bg-success-light {
                background-color: #e9f7ef;
            }

            .bg-primary-light {
                background-color: #e7f1ff;
            }

            .room-card {
                transition: all 0.3s ease;
                border: 1px solid rgba(0, 0, 0, 0.05) !important;
            }

            .room-card:hover {
                transform: translateY(-10px);
                box-shadow: 0 1rem 2.5rem rgba(0, 0, 0, 0.08) !important;
                border-color: var(--bs-danger) !important;
            }
        </style>

        <jsp:include page="/WEB-INF/view/client/layout/footer.jsp" />