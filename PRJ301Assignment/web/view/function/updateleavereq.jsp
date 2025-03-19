<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cập Nhật Đơn Xin Nghỉ Phép</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #f0f2f5; margin: 0; padding: 20px; display: flex; justify-content: center; align-items: center; min-height: 100vh; }
        .container { background-color: #fff; padding: 30px; border-radius: 12px; box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1); width: 100%; max-width: 500px; }
        h2 { text-align: center; color: #1a73e8; margin-bottom: 30px; font-size: 24px; font-weight: 600; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; color: #333; font-weight: 500; }
        input[type="date"], input[type="text"], textarea { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 6px; font-size: 14px; box-sizing: border-box; }
        textarea { resize: vertical; min-height: 100px; }
        input[type="submit"] { background-color: #2a9d8f; color: white; padding: 12px 20px; border: none; border-radius: 6px; font-size: 16px; cursor: pointer; width: 100%; transition: background-color 0.3s; }
        input[type="submit"]:hover { background-color: #21867a; }
        .back-link { display: block; text-align: center; margin-top: 20px; font-size: 14px; }
        .back-link a { color: #1a73e8; text-decoration: none; }
        .back-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Cập Nhật Đơn Xin Nghỉ Phép</h2>
        <form action="<%=request.getContextPath()%>/LeaveRequest/update?requestID=${leaveRequest.requestID}" method="POST">
            <div class="form-group">
                <label for="fromDate">Từ Ngày:</label>
                <input type="date" id="fromDate" name="fromDate" value="${leaveRequest.fromDate}" required>
            </div>
            <div class="form-group">
                <label for="toDate">Đến Ngày:</label>
                <input type="date" id="toDate" name="toDate" value="${leaveRequest.toDate}" required>
            </div>
            <div class="form-group">
                <label for="reason">Lý Do:</label>
                <textarea id="reason" name="reason" required>${leaveRequest.reason}</textarea>
            </div>
            <input type="submit" value="Cập Nhật">
        </form>
        <div class="back-link">
            <a href="<%=request.getContextPath()%>/LeaveRequest/list">Quay lại Danh Sách</a>
        </div>
    </div>
</body>
</html>