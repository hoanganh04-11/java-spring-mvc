<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <title>Truy cập bị từ chối - Smart Home</title>

            <link href="<c:url value='/css/styles.css'/>" rel="stylesheet" />
            <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        </head>

        <body>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-8 mt-5">
                        <div class="alert alert-danger shadow-sm" role="alert">
                            <i class="fas fa-exclamation-triangle me-2"></i>
                            Bạn không có quyền truy cập tài nguyên này! Vui lòng quay lại.
                        </div>
                        <div class="text-center">
                            <a href="<c:url value='/'/>" class="btn btn-success">
                                <i class="fas fa-home me-1"></i> Quay về Trang chủ
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                crossorigin="anonymous"></script>
            <script src="<c:url value='/js/scripts.js'/>"></script>
        </body>

        </html>