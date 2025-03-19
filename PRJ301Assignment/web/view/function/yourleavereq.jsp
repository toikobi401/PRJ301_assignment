<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            max-width: 900px;
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
    </style>
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
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="request" items="${leaveRequests}">
                            <tr>
                                <td>${request.userID}</td>
                                <td>${request.fromDate}</td>
                                <td>${request.toDate}</td>
                                <td>${request.reason}</td>
                                <td>${request.leaveStatus.statusName}</td>
                                <td>${request.leaveStatus.description}</td>
                                <td>${request.approvedBy != null ? request.approvedBy : 'Chưa phê duyệt'}</td>
                                <td>${request.createAt}</td>
                                <td>${request.updateAt}</td>
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