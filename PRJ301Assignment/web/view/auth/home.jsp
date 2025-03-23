<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Chủ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; font-family: 'Segoe UI', Arial, sans-serif; }
        .navbar-custom { background-color: #007bff; }
        .navbar-custom .navbar-brand, .navbar-custom .nav-link { color: white; }
        .container-custom { background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); margin-top: 20px; margin-bottom: 20px; }
        h2 { color: #007bff; font-weight: 600; margin-bottom: 20px; }
        .btn-custom { padding: 8px 16px; font-size: 14px; }
        .btn-primary-custom { background-color: #007bff; border-color: #007bff; }
        .btn-primary-custom:hover { background-color: #0056b3; border-color: #0056b3; }
        .btn-danger-custom { background-color: #dc3545; border-color: #dc3545; }
        .btn-danger-custom:hover { background-color: #c82333; border-color: #c82333; }
        /* Đảm bảo màu chữ của các nút là màu trắng */
        .btn-custom, .btn-primary-custom, .btn-danger-custom { color: white !important; }
        footer { text-align: center; padding: 10px; background-color: #007bff; color: white; position: fixed; bottom: 0; width: 100%; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-custom">
        <div class="container-fluid">
            <a class="navbar-brand" href="<%=request.getContextPath()%>/home">Quản Lý Nghỉ Phép</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/logout">Đăng Xuất</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container container-custom text-center">
        <h2>Chào mừng đến với Trang Chủ</h2>
        <c:if test="${sessionScope.user != null}">
            <p>Xin chào, ${sessionScope.user.fullName}!</p>
            <div class="d-flex flex-column gap-3">
                <a href="<%=request.getContextPath()%>/LeaveRequest/list" class="btn btn-primary-custom btn-custom">Xem Danh Sách Đơn Xin Nghỉ Của Bạn</a>
                <a href="<%=request.getContextPath()%>/LeaveRequest/create" class="btn btn-primary-custom btn-custom">Tạo Đơn Xin Nghỉ Mới</a>
                <c:set var="isAdminOrManager" value="false" />
                <c:if test="${not empty sessionScope.user.roles}">
                    <c:forEach var="role" items="${sessionScope.user.roles}">
                        <c:if test="${role.roleID == 1 || role.roleID == 2}">
                            <c:set var="isAdminOrManager" value="true" />
                        </c:if>
                    </c:forEach>
                </c:if>
                <c:if test="${isAdminOrManager}">
                    <a href="<%=request.getContextPath()%>/LeaveRequest/manage" class="btn btn-primary-custom btn-custom">Quản Lý Đơn Xin Nghỉ</a>
                </c:if>
                <c:set var="isAdmin" value="false" />
                <c:if test="${not empty sessionScope.user.roles}">
                    <c:forEach var="role" items="${sessionScope.user.roles}">
                        <c:if test="${role.roleID == 1}">
                            <c:set var="isAdmin" value="true" />
                        </c:if>
                    </c:forEach>
                </c:if>
                <c:if test="${isAdmin}">
                    <a href="<%=request.getContextPath()%>/agenda/userlist" class="btn btn-primary-custom btn-custom">Xem Tình Hình Lao Động</a>
                </c:if>
                <a href="<%=request.getContextPath()%>/logout" class="btn btn-danger-custom btn-custom">Đăng Xuất</a>
            </div>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <p>Vui lòng <a href="<%=request.getContextPath()%>/login" class="btn btn-primary-custom btn-custom">đăng nhập</a> để tiếp tục.</p>
        </c:if>
    </div>

    <footer>
        <p>© 2025 Quản Lý Nghỉ Phép. All rights reserved.</p>
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>