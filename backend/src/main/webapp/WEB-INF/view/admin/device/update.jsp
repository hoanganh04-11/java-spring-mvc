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
    <title>Cập nhật thiết bị - Smart Home</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<body class="sb-nav-fixed">

    <jsp:include page="../layout/header.jsp" />

    <div id="layoutSidenav">

        <jsp:include page="../layout/sidebar.jsp" />

        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Quản lý thiết bị</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item"><a href="/admin">Trang chủ</a></li>
                        <li class="breadcrumb-item active">Thiết bị</li>
                    </ol>
                    <div class="container mt-5">
                        <div class="row">
                            <div class="col-md-6 col-12 mx-auto">
                                <h3>Cập nhật thiết bị</h3>
                                <hr />
                                <form:form method="post" action="/admin/device/update" modelAttribute="newDevice">

                                    <div class="mb-3" style="display: none;">
                                        <label class="form-label">ID:</label>
                                        <form:input type="text" class="form-control" path="id" />
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Tên thiết bị:</label>
                                        <form:input type="text" class="form-control" path="name" />
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Trạng thái:</label>
                                        <form:select class="form-select" path="status">
                                            <form:option value="ON">Đang hoạt động</form:option>
                                            <form:option value="OFF">Đang tắt</form:option>
                                        </form:select>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Phòng:</label>
                                        <form:select class="form-select" path="room.id">
                                            <form:option value="">-- Chọn phòng --</form:option>
                                            <c:forEach var="room" items="${rooms}">
                                                <form:option value="${room.id}">${room.name}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>

                                    <button type="submit" class="btn btn-warning px-4 py-2 shadow-sm text-dark fw-bold">
                                        <i class="fas fa-save me-2"></i>Lưu cập nhật
                                    </button>

                                    <a href="/admin/device" class="btn btn-secondary px-4 py-2 shadow-sm">
                                        <i class="fas fa-arrow-left me-2"></i>Quay lại
                                    </a>

                                </form:form>
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

</body>

</html>
