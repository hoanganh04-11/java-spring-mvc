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
            <title>Dashboard - Smart Home</title>
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
                            <h1 class="mt-4">Manage Sensors</h1>
                            <ol class="breadcrumb mb-4">
                                <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                <li class="breadcrumb-item active">Sensors</li>
                            </ol>
                            <div class="container mt-5">
                                <div class="row">
                                    <div class="col-12 mx-auto">
                                        <div class="d-flex justify-content-between">
                                            <h3>Table Sensor</h3>
                                            <a href="sensor/create" class="btn btn-primary">Create Sensor</a>
                                        </div>
                            
                                        <hr />
                                        <table class="table table-bordered table-hover">
                                            <thread>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Name</th>
                                                    <th>Type</th>
                                                    <th>Threshold</th>
                                                    <th>Status</th>
                                                    <th>Room</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thread>
                                            <tbody>
                                                <c:forEach var="sensor" items="${sensors}">
                                                    <tr>
                                                        <th>${sensor.id}</th>
                                                        <td>${sensor.name}</td>
                                                        <td>${sensor.type}</td>
                                                        <td>${sensor.threshold}</td>
                                                        <td>${sensor.status}</td>
                                                        <td>${sensor.room.name}</td>
                                                        <td>
                                                            <a href="/admin/sensor/${sensor.id}" class="btn btn-success">View</a>
                                                            <a href="/admin/sensor/update/${sensor.id}" class="btn btn-warning">Update</a>
                                                            <a href="/admin/sensor/delete/${sensor.id}" class="btn btn-danger">Delete</a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                            
                            
                                            </tbody>
                                        </table>
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