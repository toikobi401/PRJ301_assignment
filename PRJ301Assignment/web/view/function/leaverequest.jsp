<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tạo Đơn Xin Nghỉ Phép</title>
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
        .error-message { color: #dc3545; background-color: #f8d7da; padding: 10px; border-radius: 5px; margin-bottom: 20px; }
        .date-display { color: #666; font-size: 14px; margin-top: 5px; }
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
        <h2 class="text-center">Tạo Đơn Xin Nghỉ Phép</h2>
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
        <form method="post" action="<%=request.getContextPath()%>/LeaveRequest/create">
            <div class="mb-3">
                <label for="reason" class="form-label">Lý do nghỉ phép:</label>
                <textarea class="form-control" id="reason" name="reason" required placeholder="Nhập lý do nghỉ phép...">${param.reason}</textarea>
            </div>
            <div class="mb-3">
                <label for="fromDate" class="form-label">Từ ngày:</label>
                <input type="date" class="form-control" id="fromDate" name="fromDate" value="${param.fromDate}" required>
                <span id="fromDateDisplay" class="date-display"></span>
            </div>
            <div class="mb-3">
                <label for="toDate" class="form-label">Đến ngày:</label>
                <input type="date" class="form-control" id="toDate" name="toDate" value="${param.toDate}" required>
                <span id="toDateDisplay" class="date-display"></span>
            </div>
            <button type="submit" class="btn btn-primary-custom btn-custom w-100">Gửi Đơn Xin Nghỉ Phép</button>
        </form>
    </div>

    <footer>
        <p>&copy; 2025 Quản Lý Nghỉ Phép. All rights reserved.</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function formatDateToVN(dateStr) {
            if (!dateStr) return 'Chưa chọn ngày';
            const date = new Date(dateStr);
            const day = String(date.getDate()).padStart(2, '0');
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const year = date.getFullYear();
            return `${day}/${month}/${year}`;
        }
        const fromDateInput = document.getElementById('fromDate');
        const fromDateDisplay = document.getElementById('fromDateDisplay');
        const toDateInput = document.getElementById('toDate');
        const toDateDisplay = document.getElementById('toDateDisplay');
        fromDateDisplay.textContent = formatDateToVN(fromDateInput.value);
        toDateDisplay.textContent = formatDateToVN(toDateInput.value);
        fromDateInput.addEventListener('change', function() {
            fromDateDisplay.textContent = formatDateToVN(this.value);
        });
        toDateInput.addEventListener('change', function() {
            toDateDisplay.textContent = formatDateToVN(this.value);
        });
    </script>
</body>
</html>