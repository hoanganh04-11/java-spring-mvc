<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />
                <title>Thêm cảm biến - IoT Smart Home</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

                
            </head>

            <body class="sb-nav-fixed">
                <!-- header -->
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <!-- sidebar -->
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Quản lý danh sách cảm biến</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Sensors</li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Thêm mới một cảm biến</h3>
                                            <hr />
                                            <form:form method="post" action="/admin/sensor/create"
                                                modelAttribute="newSensor" class="row" 
                                                enctype="multipart/form-data">

                                                <div class="mb-3 col-12 col-md-6">
                                                    <c:set var="errorName">
                                                        <form:errors path="name" cssClass="invalid-feedback"/>
                                                    </c:set>
                                                    <label class="form-label">Tên cảm biến:</label>
                                                    <form:input type="text" class="form-control ${not empty errorName ? 'is-invalid' : ''}" path="name"/>
                                                    ${errorName}
                                                </div>

                                                
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Loại cảm biến:</label>
                                                    <form:select class="form-select" path="type" required="required">
                                                        <form:option value="TEMPERATURE">Nhiệt độ</form:option>
                                                        <form:option value="LIGHT">Ánh sáng</form:option>
                                                        <form:option value="MOTION">Chuyển động</form:option>
                                                        <form:option value="GAS">Khí Gas</form:option>
                                                        <form:option value="SMOKE">Khói</form:option>
                                                    </form:select>
                                                </div>

                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Trạng thái:</label>
                                                    <form:select class="form-select" path="status" required="required">
                                                        <form:option value="ON">Đang hoạt động</form:option>
                                                        <form:option value="OFF">Đang tắt</form:option>
                                                    </form:select>
                                                </div>

                                                
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Phòng:</label>
                                                    <form:select class="form-select" path="room.id" required="required">
                                                        <form:option value="">-- Chọn phòng --</form:option>
                                                        <c:forEach var="room" items="${rooms}">
                                                            <form:option value="${room.id}">${room.name}</form:option>
                                                        </c:forEach>
                                                    </form:select>
                                                </div>


                                                
                                                    <button type="submit" class="btn btn-primary">Tạo mới</button>
                                                    <a href="/admin/sensor" class="btn btn-secondary mt-2">Quay lại</a>

                                            </form:form>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </main>
                        <!-- footer -->
                        <jsp:include page="../layout/footer.jsp" />

                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="js/scripts.js"></script>

            </body>

            </html>