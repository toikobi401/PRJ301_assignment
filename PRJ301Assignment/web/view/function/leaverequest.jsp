<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tạo Đơn Xin Nghỉ</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .form-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 400px;
        }
        .form-container h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .form-container label {
            display: block;
            margin-bottom: 5px;
        }
        .form-container input[type="date"],
        .form-container textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-container input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .form-container input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h1>Tạo đơn xin nghỉ</h1>
    
    <%-- Hiển thị thông tin người dùng từ user object --%>
    <%
        data.User user = (data.User) request.getAttribute("user");
        if (user != null) {
    %>
        <p>UserID: <%= user.getUserID() %></p>
        <p>FullName: <%= user.getFullName() %></p>
    <%
        } else {
    %>
        <p>Không tìm thấy thông tin người dùng.</p>
    <%
        }
    %>

    <%-- Form để nhập thông tin LeaveRequest --%>
    <form action="create" method="post">
        <label>Start Date:</label>
        <input type="date" name="startDate" required><br>

        <label>End Date:</label>
        <input type="date" name="endDate" required><br>

        <label>Reason:</label>
        <textarea name="reason" required></textarea><br>

        <button type="submit">Submit</button>
    </form>

    <a href="view/auth/home.jsp">Quay lại trang chủ</a>
</body>
</html>