<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Lý Đơn Xin Nghỉ</title>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 20px;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            max-width: 1200px;
            margin: 0 auto;
        }
        h2 {
            text-align: center;
            color: #1a73e8;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #1a73e8;
            color: white;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .status-pending {
            color: #ff9800;
            font-weight: bold;
        }
        /* Đảm bảo cột Hành Động có đủ chiều rộng */
        th:last-child, td:last-child {
            width: 150px; /* Điều chỉnh chiều rộng cột Hành Động */
            text-align: center; /* Căn giữa các nút */
        }
        .action-buttons {
            display: flex;
            justify-content: center;
            gap: 10px; /* Khoảng cách giữa các nút */
            flex-wrap: nowrap; /* Không cho phép xuống dòng */
        }
        .btn {
            padding: 8px 16px;
            border-radius: 4px;
            text-decoration: none;
            color: white;
            font-size: 14px;
            display: inline-block; /* Đảm bảo nút hiển thị đúng */
        }
        .btn-approve {
            background-color: #4CAF50;
        }
        .btn-approve:hover {
            background-color: #45a049;
        }
        .btn-reject {
            background-color: #e63946;
        }
        .btn-reject:hover {
            background-color: #d00000;
        }
        .pagination {
            text-align: center;
        }
        .pagination a {
            padding: 8px 16px;
            text-decoration: none;
            color: #1a73e8;
            border: 1px solid #ddd;
            margin: 0 4px;
            border-radius: 4px;
        }
        .pagination a.active {
            background-color: #1a73e8;
            color: white;
            border: 1px solid #1a73e8;
        }
        .pagination a:hover:not(.active) {
            background-color: #ddd;
        }
        .back-link {
            text-align: center;
            margin-top: 20px;
        }
        .back-link a {
            color: #1a73e8;
            text-decoration: none;
        }
        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Danh Sách Đơn Xin Nghỉ Phép Đang Chờ Duyệt</h2>
        <table>
            <thead>
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
                            <div class="action-buttons">
                                <a href="<%=request.getContextPath()%>/LeaveRequest/approve?id=${request.requestID}" class="btn btn-approve">Duyệt</a>
                                <a href="<%=request.getContextPath()%>/LeaveRequest/reject?id=${request.requestID}" class="btn btn-reject">Từ chối</a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="pagination">
            <c:if test="${totalPages > 0}">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="<%=request.getContextPath()%>/LeaveRequest/manage?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                </c:forEach>
            </c:if>
        </div>

        <div class="back-link">
            <a href="<%=request.getContextPath()%>/home">Quay về Trang Chủ</a>
        </div>
    </div>
</body>
</html>