<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Đơn Xin Nghỉ Phép</title>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background-color: #f4f7f9;
            margin: 0;
            padding: 40px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .table-container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            max-width: 1000px;
            width: 100%;
        }

        .table-container h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 25px;
            font-size: 24px;
            font-weight: 600;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #dfe6e9;
        }

        th {
            background-color: #3498db;
            color: white;
            font-weight: 500;
        }

        tr:hover {
            background-color: #f1f3f5;
        }

        .no-data {
            text-align: center;
            color: #7f8c8d;
            padding: 20px;
        }

        .action-buttons {
            display: flex;
            gap: 10px;
            justify-content: flex-start;
            align-items: center;
        }

        .action-buttons a, .action-buttons button {
            text-decoration: none;
            padding: 6px 12px;
            border-radius: 4px;
            font-size: 14px;
            color: white;
            border: none;
            cursor: pointer;
        }

        .action-buttons .delete-btn {
            background-color: #e74c3c;
        }

        .action-buttons .delete-btn:hover {
            background-color: #c0392b;
        }

        .action-buttons .update-btn {
            background-color: #2ecc71;
        }

        .action-buttons .update-btn:hover {
            background-color: #27ae60;
        }
    </style>
    <script>
        function deleteLeaveRequest(requestID) {
            if (confirm("Bạn có chắc chắn muốn xóa đơn xin nghỉ phép với ID: " + requestID + "?")) {
                // Tạo form ẩn để gửi yêu cầu POST
                const form = document.createElement("form");
                form.method = "POST";
                form.action = "<%=request.getContextPath()%>/LeaveRequest/delete";

                const input = document.createElement("input");
                input.type = "hidden";
                input.name = "requestID";
                input.value = requestID;
                form.appendChild(input);

                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</head>
<body>
    <div class="table-container">
        <h2>Danh Sách Đơn Xin Nghỉ Phép</h2>
        <c:choose>
            <c:when test="${not empty leaveRequests}">
                <table>
                    <thead>
                        <tr>
                            <th>UserID</th>
                            <th>Từ Ngày</th>
                            <th>Đến Ngày</th>
                            <th>Lý Do</th>
                            <th>Trạng Thái</th>
                            <th>Mô Tả Trạng Thái</th>
                            <th>Người Phê Duyệt</th>
                            <th>Ngày Tạo</th>
                            <th>Ngày Cập Nhật</th>
                            <th>Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="request" items="${leaveRequests}">
                            <tr>
                                <td>${request.userID}</td>
                                <td>${request.fromDate}</td>
                                <td>${request.toDate}</td>
                                <td>${request.reason}</td>
                                <td>${request.leaveStatus != null ? request.leaveStatus.statusName : 'N/A'}</td>
                                <td>${request.leaveStatus != null ? request.leaveStatus.description : 'N/A'}</td>
                                <td>${request.approvedBy != null ? request.approvedBy : 'Chưa phê duyệt'}</td>
                                <td>${request.createAt}</td>
                                <td>${request.updateAt}</td>
                                <td class="action-buttons">
                                    <button class="delete-btn" onclick="deleteLeaveRequest(${request.requestID})">Xóa</button>
                                    <a href="<%=request.getContextPath()%>/view/function/updateleavereq.jsp?requestID=${request.requestID}" class="update-btn">Cập Nhật</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p class="no-data">Không có đơn xin nghỉ phép nào.</p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html> 