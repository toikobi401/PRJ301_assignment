<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thành Công</title>
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

        .success-container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            width: 100%;
            text-align: center;
        }

        .success-container h2 {
            color: #27ae60;
            margin-bottom: 20px;
            font-size: 24px;
            font-weight: 600;
        }

        .success-container p {
            color: #34495e;
            font-size: 16px;
            margin-bottom: 25px;
        }

        .success-container a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-size: 14px;
            transition: background-color 0.3s ease;
        }

        .success-container a:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
    <div class="success-container">
        <h2>Tạo Đơn Xin Nghỉ Thành Công!</h2>
        <p>Đơn xin nghỉ của bạn đã được gửi thành công. Nhấn vào liên kết dưới đây để quay lại trang chủ.</p>
        <a href="<%=request.getContextPath()%>/view/auth/home.jsp">Quay về Trang Chủ</a>
    </div>
</body>
</html>