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
                <title>Xóa người dùng - Smart Home</title>
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
                                <h1 class="mt-4">Quản lý người dùng</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Trang chủ</a></li>
                                    <li class="breadcrumb-item active">Người dùng</li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-12 mx-auto">
                                            <div class="d-flex justify-content-between">
                                                <h3>Xóa người dùng với Id = ${id}</h3>
                                            </div>
                                            <hr />
                                
                                            <div class="alert alert-danger">
                                                Bạn có chắc muốn xóa người dùng này ?
                                            </div>
                                            <form:form method="post" action="/admin/user/delete" 
                                                modelAttribute="deleteUser">
                                                <div class="mb-3" style="display: none;">
                                                    <label class="form-label">ID:</label>
                                                    <form:input value="${id}" type="text" class="form-control" path="id" />
                                                </div>
                                                <button type="submit" class="btn btn-danger px-4 py-2 shadow-sm">
                                                    <i class="fas fa-trash-alt me-2"></i>Xác nhận xóa
                                                </button>
                                                
                                                <a href="/admin/user" class="btn btn-secondary px-4 py-2 shadow-sm">
                                                    <i class="fas fa-undo me-2"></i>Quay lại
                                                </a>

                                            </form:form>
                                        </div>
                                
                                    </div>
                            </div>
                        </main>
                        
                        <jsp:include page="../layout/footer.jsp"/>

                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="js/scripts.js"></script>

            </body>

            </html>