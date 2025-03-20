<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Đơn Xin Nghỉ Phép Của Bạn</title>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 1200px;
        }

        h2 {
            text-align: center;
            color: #1a73e8;
            margin-bottom: 30px;
            font-size: 28px;
            font-weight: 600;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 15px;
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #e0e0e0;
        }

        th {
            background-color: #1a73e8;
            color: white;
            font-weight: 600;
            text-transform: uppercase;
        }

        tr:hover {
            background-color: #f5f7fa;
        }

        .no-data {
            text-align: center;
            color: #666;
            padding: 30px;
            font-size: 16px;
        }

        .action-buttons {
            display: flex;
            gap: 10px;
        }

        .btn {
            padding: 8px 16px;
            border-radius: 6px;
            font-size: 14px;
            text-decoration: none;
            color: white;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn-delete {
            background-color: #e63946;
        }

        .btn-delete:hover {
            background-color: #d00000;
        }

        .btn-update {
            background-color: #2a9d8f;
        }

        .btn-update:hover {
            background-color: #21867a;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            font-size: 16px;
        }

        .back-link a {
            color: #1a73e8;
            text-decoration: none;
        }

        .back-link a:hover {
            text-decoration: underline;
        }

        .pagination {
            text-align: center;
            margin-top: 20px;
        }

        .pagination a {
            color: #1a73e8;
            text-decoration: none;
            padding: 8px 12px;
            margin: 0 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .pagination a:hover {
            background-color: #1a73e8;
            color: white;
        }

        .pagination .current {
            background-color: #1a73e8;
            color: white;
            padding: 8px 12px;
            margin: 0 5px;
            border: 1px solid #1a73e8;
            border-radius: 5px;
        }

        @media (max-width: 768px) {
            table, th, td {
                display: block;
                width: 100%;
            }
            th, td {
                padding: 10px;
            }
            .action-buttons {
                justify-content: center;
            }
        }
    </style>
    <script>
        function deleteLeaveRequest(requestID) {
            if (confirm("Bạn có chắc chắn muốn xóa đơn xin nghỉ phép ?")) {
                window.location.href = "<%=request.getContextPath()%>/LeaveRequest/delete?requestID=" + requestID;
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Danh Sách Đơn Xin Nghỉ Phép Của Bạn</h2>
        <c:choose>
            <c:when test="${not empty leaveRequests}">
                <table>
                    <thead>
                        <tr>
                            <th>Từ Ngày</th>
                            <th>Đến Ngày</th>
                            <th>Lý Do</th>
                            <th>Trạng Thái</th>
                            <th>Mô Tả</th>
                            <th>Người Phê Duyệt</th>
                            <th>Ngày Tạo</th>
                            <th>Ngày Cập Nhật</th>
                            <th>Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="request" items="${leaveRequests}">
                            <tr>
                                <td><fmt:formatDate value="${request.fromDate}" pattern="dd/MM/yyyy"/></td>
                                <td><fmt:formatDate value="${request.toDate}" pattern="dd/MM/yyyy"/></td>
                                <td>${request.reason}</td>
                                <td>${request.leaveStatus != null ? request.leaveStatus.statusName : 'Chưa xác định'}</td>
                                <td>${request.leaveStatus != null ? request.leaveStatus.description : 'N/A'}</td>
                                <td>${request.approvedBy != null ? request.approvedBy : 'Chưa phê duyệt'}</td>
                                <td><fmt:formatDate value="${request.createAt}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                                <td><fmt:formatDate value="${request.updateAt}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                                <td class="action-buttons">
                                    <button class="btn btn-delete" onclick="deleteLeaveRequest(${request.requestID})">Xóa</button>
                                    <a href="<%=request.getContextPath()%>/LeaveRequest/update?requestID=${request.requestID}" class="btn btn-update">Cập Nhật</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Phân trang -->
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="<%=request.getContextPath()%>/LeaveRequest/list?page=${currentPage - 1}">Trang trước</a>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <span class="current">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="<%=request.getContextPath()%>/LeaveRequest/list?page=${i}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <a href="<%=request.getContextPath()%>/LeaveRequest/list?page=${currentPage + 1}">Trang sau</a>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <p class="no-data">Hiện tại không có đơn xin nghỉ phép nào.</p>
            </c:otherwise>
        </c:choose>
        <div class="back-link">
            <a href="<%=request.getContextPath()%>/home">Quay về Trang Chủ</a>
        </div>
    </div>
</body>
</html>