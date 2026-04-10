<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <jsp:include page="/WEB-INF/view/client/layout/header.jsp">
            <jsp:param name="title" value="Cảm biến - SmartHome IoT" />
        </jsp:include>

        <div class="container py-5 mt-5">
            <div class="text-center mx-auto mb-5" style="max-width: 700px;">
                <h1 class="display-4 fw-bold">Hệ thống Cảm biến</h1>
                <p class="text-muted">Theo dõi các thông số môi trường từ tất cả các cảm biến được lắp đặt trong ngôi
                    nhà của bạn.</p>
            </div>

            <c:if test="${empty sensors}">
                <div class="alert alert-info rounded-4 shadow-sm border-0 p-4">
                    <div class="d-flex align-items-center gap-3">
                        <i class="bi bi-info-circle-fill fs-2"></i>
                        <div>
                            <h5 class="mb-1 fw-bold">Chưa cấu hình cảm biến</h5>
                            <p class="mb-0">Hệ thống hiện chưa có cảm biến nào được kích hoạt. Vui lòng kiểm tra lại kết
                                nối phần cứng.</p>
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="row g-4">
                <c:forEach var="sensor" items="${sensors}">
                    <div class="col-md-6 col-lg-4">
                        <div class="card border-0 shadow-sm rounded-4 overflow-hidden h-100 sensor-card">
                            <div class="card-body p-4">
                                <div class="d-flex justify-content-between align-items-start mb-3">
                                    <div class="bg-light rounded-circle d-flex align-items-center justify-content-center"
                                        style="width: 60px; height: 60px;">
                                        <i class="bi bi-cpu-fill text-success fs-2"></i>
                                    </div>
                                    <span
                                        class="badge ${sensor.status == 'ACTIVE' ? 'bg-success' : 'bg-secondary'} rounded-pill px-3 py-2">
                                        ${sensor.status == 'ACTIVE' ? 'Hoạt động' : 'Đang tắt'}
                                    </span>
                                </div>

                                <h4 class="fw-bold text-dark mb-1">${sensor.name}</h4>
                                <p class="text-muted small mb-3">
                                    <i class="bi bi-door-open-fill text-danger me-1"></i> ${sensor.room.name}
                                </p>

                                <div class="bg-light rounded-4 p-3 mb-3">
                                    <div class="row text-center">
                                        <div class="col-12">
                                            <p class="text-muted small mb-1">Loại</p>
                                            <span class="fw-bold text-dark">${sensor.type}</span>
                                        </div>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <p class="text-muted small mb-2">Dữ liệu mới nhất:</p>
                                    <c:choose>
                                        <c:when test="${not empty sensor.latestData}">
                                            <div class="d-flex align-items-baseline gap-2">
                                                <span
                                                    class="display-6 fw-bold text-primary">${sensor.latestData[0].value}</span>
                                                <small class="text-muted">${sensor.latestData[0].recordedAt}</small>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted fst-italic">Phần cứng chưa gửi dữ liệu</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="card-footer bg-white border-0 p-4 pt-0">
                                <a href="/client/sensor/${sensor.id}"
                                    class="btn btn-outline-success w-100 rounded-pill py-2">
                                    <i class="bi bi-graph-up me-2"></i> Chi tiết & Biểu đồ
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
<style>
            .sensor-card {
                transition: all 0.3s ease;
                border: 1px solid rgba(0, 0, 0, 0.05) !important;
            }

            .sensor-card:hover {
                transform: translateY(-8px);
                box-shadow: 0 1rem 2.5rem rgba(0, 0, 0, 0.08) !important;
                border-color: var(--bs-success) !important;
            }
        </style>

<jsp:include page="/WEB-INF/view/client/layout/footer.jsp" />

