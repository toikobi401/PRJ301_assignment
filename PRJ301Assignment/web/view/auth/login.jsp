<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; font-family: 'Segoe UI', Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-container { background-color: white; padding: 40px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); width: 100%; max-width: 400px; }
        h2 { color: #007bff; font-weight: 600; margin-bottom: 20px; }
        .btn-custom { padding: 8px 16px; font-size: 14px; }
        .btn-success-custom { background-color: #28a745; border-color: #28a745; }
        .btn-success-custom:hover { background-color: #218838; border-color: #218838; }
        .error-message { color: #dc3545; background-color: #f8d7da; padding: 10px; border-radius: 5px; margin-top: 10px; }
    </style>
</head>
<body>
    <div class="login-container">
        <h2 class="text-center">Đăng Nhập</h2>
        <form action="<%=request.getContextPath()%>/login" method="post">
            <div class="mb-3">
                <input type="text" class="form-control" name="username" placeholder="Tên đăng nhập" required>
            </div>
            <div class="mb-3">
                <input type="password" class="form-control" name="password" placeholder="Mật khẩu" required>
            </div>
            <button type="submit" class="btn btn-success-custom btn-custom w-100">Đăng Nhập</button>
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>