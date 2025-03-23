<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Lý Đơn Xin Nghỉ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; font-family: 'Segoe UI', Arial, sans-serif; }
        .navbar-custom { background-color: #007bff; }
        .navbar-custom .navbar-brand, .navbar-custom .nav-link { color: white; }
        .container-custom { background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); margin-top: 20px; margin-bottom: 20px; }
        h2 { color: #007bff; font-weight: 600; margin-bottom: 20px; }
        .btn-custom { padding: 8px 16px; font-size: 14px; }
        .btn-success-custom { background-color: #28a745; border-color: #28a745; }
        .btn-success-custom:hover { background-color: #218838; border-color: #218838; }
        .btn-danger-custom { background-color: #dc3545; border-color: #dc3545; }
        .btn-danger-custom:hover { background-color: #c82333; border-color: #c82333; }
        .status-pending { color: #ff9800; font-weight: bold; }
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

    <div class="container container-custom">
        <h2 class="text-center">Danh Sách Đơn Xin Nghỉ Phép Đang Chờ Duyệt</h2>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID Đơn</th>
                        <th>Họ Tên</th>
                        <th>Từ Ngày</th>
                        <th>Đến Ngày</th>
                        <th>Lý Do</th>
                        <th>Trạng Thái</th>
                        <th>Ngày Tạo</th>
                        <th>Ngày Cập Nhật</th>
                        <th>Hành Động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="requestWithUser" items="${leaveRequests}">
                        <c:set var="request" value="${requestWithUser[0]}" />
                        <c:set var="fullName" value="${requestWithUser[1]}" />
                        <tr>
                            <td>${request.requestID}</td>
                            <td>${fullName}</td>
                            <td><fmt:formatDate value="${request.fromDate}" pattern="dd/MM/yyyy"/></td>
                            <td><fmt:formatDate value="${request.toDate}" pattern="dd/MM/yyyy"/></td>
                            <td>${request.reason}</td>
                            <td class="status-pending">${request.leaveStatus.statusName}</td>
                            <td><fmt:formatDate value="${request.createAt}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                            <td><fmt:formatDate value="${request.updateAt}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                            <td>
                                <a href="<%=request.getContextPath()%>/LeaveRequest/approve?id=${request.requestID}" class="btn btn-success-custom btn-custom">Duyệt</a>
                                <a href="<%=request.getContextPath()%>/LeaveRequest/reject?id=${request.requestID}" class="btn btn-danger-custom btn-custom">Từ chối</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="<%=request.getContextPath()%>/LeaveRequest/manage?page=${i}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
        <div class="text-center mt-3">
            <a href="<%=request.getContextPath()%>/home" class="btn btn-primary-custom btn-custom">Quay về Trang Chủ</a>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 Quản Lý Nghỉ Phép. All rights reserved.</p>
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>