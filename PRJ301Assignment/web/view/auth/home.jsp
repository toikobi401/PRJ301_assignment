<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        Object userObj = session.getAttribute("user");
        if (userObj instanceof data.User) {
            data.User user = (data.User) userObj;
            int userId = user.getUserID();
        %>
        <p>UserID: <%= userId %></p>
        <p>Full Name: <%= user.getFullName() %></p>
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
            <a href="view/function/leaverequest.jsp">Tạo đơn xin nghỉ</a>
            <a href="view/function/managerequest.jsp">Duyệt đơn xin nghỉ</a>
            <a href="view/function/yourleavereq.jsp">Xem danh sách đơn xin nghỉ của bạn</a>
        </div>
    </div>
</body>
</html>