<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="data.User" %>
<html>
<head>
    <title>Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 0;
        }
        .header {
            background-color: #4CAF50;
            color: white;
            padding: 15px;
            text-align: center;
            font-size: 24px;
        }
        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 80vh;
        }
        .menu {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }
        .menu a {
            background-color: #4CAF50;
            color: white;
            padding: 15px 30px;
            text-decoration: none;
            border-radius: 5px;
            font-size: 18px;
            text-align: center;
            transition: background-color 0.3s;
        }
        .menu a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="header">
        Welcome to Leave Management System
        <%
            User userObj = (User) session.getAttribute("user");
            if (userObj != null) {
        %>
                <p>UserID: <%= userObj.getUserID() %></p> <!-- Hiển thị userID từ session -->
                <p>Full Name: <%= userObj.getFullName() != null ? userObj.getFullName() : "Not available" %></p>
        <%
            } else {
        %>
                <p>Bạn cần đăng nhập.</p>
        <%
            }
        %>
    </div>
    <div class="container">
        <div class="menu">
            <a href="<%=request.getContextPath()%>/LeaveRequest/create">Tạo đơn xin nghỉ</a>
            <a href="<%=request.getContextPath()%>/LeaveRequest/manage">Duyệt đơn xin nghỉ</a>
            <a href="<%=request.getContextPath()%>/LeaveRequest/list">Xem danh sách đơn xin nghỉ của bạn</a>
        </div>
    </div>
</body>
</html>