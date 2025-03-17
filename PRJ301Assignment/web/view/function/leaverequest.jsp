<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tạo Đơn Xin Nghỉ Phép</title>
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

        .form-container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            width: 100%;
        }

        .form-container h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 25px;
            font-size: 24px;
            font-weight: 600;
        }

        .form-container label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: #34495e;
        }

        .form-container input[type="date"],
        .form-container textarea {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #dfe6e9;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 14px;
            transition: border-color 0.3s ease;
        }

        .form-container input[type="date"]:focus,
        .form-container textarea:focus {
            border-color: #3498db;
            outline: none;
        }

        .form-container textarea {
            resize: vertical;
            min-height: 120px;
        }

        .form-container input[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 500;
            transition: background-color 0.3s ease;
        }

        .form-container input[type="submit"]:hover {
            background-color: #2980b9;
        }

        /* Responsive design */
        @media (max-width: 480px) {
            .form-container {
                padding: 20px;
            }
            .form-container h2 {
                font-size: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Tạo Đơn Xin Nghỉ Phép</h2>
        <form method="post" action="<%=request.getContextPath()%>/LeaveRequest/create">
            <label for="reason">Lý do nghỉ phép:</label>
            <textarea id="reason" name="reason" required placeholder="Nhập lý do nghỉ phép..."></textarea>
            
            <label for="fromDate">Từ ngày:</label>
            <input type="date" id="fromDate" name="fromDate" required>
            
            <label for="toDate">Đến ngày:</label>
            <input type="date" id="toDate" name="toDate" required>
            
            <input type="submit" value="Gửi Đơn Xin Nghỉ Phép">
        </form>
    </div>
</body>
</html>