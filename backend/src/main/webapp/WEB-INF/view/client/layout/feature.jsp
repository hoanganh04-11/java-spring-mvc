<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container-fluid py-5">
    <div class="container py-5">
        <div class="text-center mx-auto mb-5" style="max-width: 700px;">
            <h1 class="display-5 fw-bold">Danh mục quản lý</h1>
            <p class="text-muted">Chọn khu vực bạn muốn theo dõi hoặc điều khiển trong hệ thống nhà thông minh.</p>
        </div>

        <div class="row g-4 justify-content-center">
            <div class="col-md-6 col-lg-4">
                <a href="/client/device" class="text-decoration-none">
                    <div class="card border-0 shadow-sm rounded-4 h-100 menu-card">
                        <div class="card-body p-4 text-center">
                            <h3 class="fw-bold mb-3 text-dark">Thiết bị</h3>
                            <p class="text-muted mb-0">Bật/tắt và theo dõi trạng thái các thiết bị trong nhà.</p>
                        </div>
                    </div>
                </a>
            </div>

            <div class="col-md-6 col-lg-4">
                <a href="/client/sensor-list" class="text-decoration-none">
                    <div class="card border-0 shadow-sm rounded-4 h-100 menu-card">
                        <div class="card-body p-4 text-center">
                            <h3 class="fw-bold mb-3 text-dark">Cảm biến</h3>
                            <p class="text-muted mb-0">Xem dữ liệu đo lường mới nhất từ các cảm biến môi trường.</p>
                        </div>
                    </div>
                </a>
            </div>

            <div class="col-md-6 col-lg-4">
                <a href="/client/room-list" class="text-decoration-none">
                    <div class="card border-0 shadow-sm rounded-4 h-100 menu-card">
                        <div class="card-body p-4 text-center">
                            <h3 class="fw-bold mb-3 text-dark">Phòng</h3>
                            <p class="text-muted mb-0">Quản lý theo từng phòng để xem cảm biến và thiết bị liên quan.</p>
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>

<style>
    .menu-card {
        transition: all 0.25s ease;
        border: 1px solid rgba(0, 0, 0, 0.05) !important;
    }

    .menu-card:hover {
        transform: translateY(-6px);
        box-shadow: 0 1rem 2rem rgba(0, 0, 0, 0.08) !important;
    }
</style>
