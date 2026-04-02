<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" /> 
    <title>Trang chủ - Smart Home</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
    <link href="css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp"/> 
    <div id="layoutSidenav">
        <jsp:include page="../layout/sidebar.jsp"/>
        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Trang chủ</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item active">Trang chủ</li>
                    </ol>
                    
                    <!-- Hàng thống kê chính -->

                    <div class="row g-4 mb-4">
                        <div class="col-xl-3 col-md-6">
                            <div class="card text-white shadow stat-card" style="background: linear-gradient(135deg,#1a73e8,#0d47a1)">
                                <div class="card-body d-flex align-items-center">
                                    <i class="bi bi-door-open stat-icon me-3"></i>
                                    <div>
                                        <h2 class="mb-0">${countRooms}</h2>
                                        <span>Phòng</span>
                                    </div>
                                </div>
                                <div class="card-footer bg-transparent border-0">
                                    <a href="/admin/room" class="text-white text-decoration-none small">
                                        Quản lý phòng <i class="bi bi-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    
                        <div class="col-xl-3 col-md-6">
                            <div class="card text-white shadow stat-card" style="background: linear-gradient(135deg,#0f9d58,#00600f)">
                                <div class="card-body d-flex align-items-center">
                                    <i class="bi bi-cpu stat-icon me-3"></i>
                                    <div>
                                        <h2 class="mb-0">${countSensors}</h2>
                                        <span>Cảm biến</span>
                                    </div>
                                </div>
                                <div class="card-footer bg-transparent border-0">
                                    <a href="/admin/sensor" class="text-white text-decoration-none small">
                                        Quản lý cảm biến <i class="bi bi-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    
                        <div class="col-xl-3 col-md-6">
                            <div class="card text-white shadow stat-card" style="background: linear-gradient(135deg,#f4511e,#b71c1c)">
                                <div class="card-body d-flex align-items-center">
                                    <i class="bi bi-plug stat-icon me-3"></i>
                                    <div>
                                        <h2 class="mb-0">${countDevices}</h2>
                                        <span>Thiết bị</span>
                                    </div>
                                </div>
                                <div class="card-footer bg-transparent border-0">
                                    <a href="/admin/device" class="text-white text-decoration-none small">
                                        Quản lý thiết bị <i class="bi bi-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Hàng thiết bị bật/tắt -->
                    <div class="row g-4 mb-4">
                        <div class="col-md-6">
                            <div class="card shadow-sm">
                                <div class="card-header bg-success text-white">
                                    <i class="bi bi-lightning-charge-fill"></i> Thiết bị đang BẬT
                                </div>
                                <div class="card-body">
                                    <h1 class="display-4 fw-bold text-success">${countDevicesOn}</h1>
                                    <p class="text-muted">/ ${countDevices} thiết bị tổng</p>
                                    <a href="/admin/device" class="btn btn-outline-success btn-sm">
                                        <i class="bi bi-plug"></i> Xem tất cả thiết bị
                                    </a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="card shadow-sm">
                                <div class="card-header bg-secondary text-white">
                                    <i class="bi bi-power"></i> Thiết bị đang TẮT
                                </div>
                                <div class="card-body">
                                    <h1 class="display-4 fw-bold text-secondary">${countDevicesOff}</h1>
                                    <p class="text-muted">/ ${countDevices} thiết bị tổng</p>
                                    <a href="/admin/device" class="btn btn-outline-secondary btn-sm">
                                        <i class="bi bi-plug"></i> Xem tất cả thiết bị
                                    </a>
                                </div>
                            </div>
                        </div>
                        
                    </div>

                </div>
            </main>
            <jsp:include page="../layout/footer.jsp" />
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
    <script src="js/scripts.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
    <script src="js/chart-area-demo.js"></script>
    <script src="js/chart-bar-demo.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"
        crossorigin="anonymous"></script>
    <script src="js/datatables-simple-demo.js"></script>
</body>

</html>