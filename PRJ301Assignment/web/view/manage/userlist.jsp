<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách User</title>
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
        /* Đảm bảo màu chữ của các nút là màu trắng */
        .btn-custom, .btn-primary-custom, .page-link { color: white !important; }
        /* Điều chỉnh màu chữ của các nút phân trang */
        .page-link { background-color: #007bff; border-color: #007bff; }
        .page-link:hover { background-color: #0056b3; border-color: #0056b3; }
        .page-item.active .page-link { background-color: #0056b3; border-color: #0056b3; }
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
        <h2 class="text-center">Danh Sách User</h2>
        <form class="d-flex justify-content-center mb-4" method="get" action="<%=request.getContextPath()%>/agenda/userlist">
            <input type="text" class="form-control w-50 me-2" name="search" placeholder="Nhập tên để tìm kiếm..." value="${param.search}">
            <button type="submit" class="btn btn-primary-custom btn-custom">Tìm kiếm</button>
        </form>
        <c:choose>
            <c:when test="${not empty users}">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>Tên User</th>
                                <th>Hành Động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td>${user.fullName}</td>
                                    <td>
                                        <a href="<%=request.getContextPath()%>/agenda/userworkingdetail?userId=${user.userID}" class="btn btn-primary-custom btn-custom">Xem Chi Tiết</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="<%=request.getContextPath()%>/agenda/userlist?page=${currentPage - 1}&search=${param.search}">Trang trước</a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="<%=request.getContextPath()%>/agenda/userlist?page=${i}&search=${param.search}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="<%=request.getContextPath()%>/agenda/userlist?page=${currentPage + 1}&search=${param.search}">Trang sau</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:when>
            <c:otherwise>
                <p class="text-center text-muted">Hiện tại không có user nào phù hợp với tìm kiếm.</p>
            </c:otherwise>
        </c:choose>
        <div class="text-center mt-3">
            <a href="<%=request.getContextPath()%>/home" class="btn btn-primary-custom btn-custom">Quay về Trang Chủ</a>
        </div>
    </div>

    <footer>
        <p>© 2025 Quản Lý Nghỉ Phép. All rights reserved.</p>
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>