<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tạo Đơn Xin Nghỉ Phép</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            padding: 20px;
        }
        h2 {
            color: #333;
        }
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            margin: 0 auto;
        }
        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }
        input[type="date"], textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea {
            resize: vertical;
            min-height: 100px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h2>Tạo Đơn Xin Nghỉ Phép</h2>
<form method="post" action="<%=request.getContextPath()%>/LeaveRequest/create">
        <label for="reason">Lý do nghỉ phép:</label>
        <textarea id="reason" name="reason" required></textarea>
        
        <label for="fromDate">Từ ngày:</label>
        <input type="date" id="fromDate" name="fromDate" required>
        
        <label for="toDate">Đến ngày:</label>
        <input type="date" id="toDate" name="toDate" required>
        
        <input type="submit" value="Gửi Đơn Xin Nghỉ Phép">
    </form>
</body>
</html>