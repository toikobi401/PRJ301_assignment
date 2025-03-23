<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        .error-message {
            color: #e74c3c;
            background-color: #f9e1e1;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
            font-size: 14px;
        }

        .date-display {
            color: #666;
            font-size: 14px;
            margin-top: -15px;
            margin-bottom: 20px;
            display: block;
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
        <!-- Hiển thị thông báo lỗi nếu có -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                ${errorMessage}
            </div>
        </c:if>
        <form method="post" action="<%=request.getContextPath()%>/LeaveRequest/create">
            <label for="reason">Lý do nghỉ phép:</label>
            <textarea id="reason" name="reason" required placeholder="Nhập lý do nghỉ phép...">${param.reason}</textarea>
            
            <label for="fromDate">Từ ngày:</label>
            <input type="date" id="fromDate" name="fromDate" value="${param.fromDate}" required>
            <span id="fromDateDisplay" class="date-display"></span>
            
            <label for="toDate">Đến ngày:</label>
            <input type="date" id="toDate" name="toDate" value="${param.toDate}" required>
            <span id="toDateDisplay" class="date-display"></span>
            
            <input type="submit" value="Gửi Đơn Xin Nghỉ Phép">
        </form>
    </div>

    <script>
        // Hàm chuyển đổi định dạng ngày từ yyyy-MM-dd sang dd/MM/yyyy
        function formatDateToVN(dateStr) {
            if (!dateStr) return 'Chưa chọn ngày';
            const date = new Date(dateStr);
            const day = String(date.getDate()).padStart(2, '0');
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const year = date.getFullYear();
            return `${day}/${month}/${year}`;
        }

        // Lấy các input và span hiển thị
        const fromDateInput = document.getElementById('fromDate');
        const fromDateDisplay = document.getElementById('fromDateDisplay');
        const toDateInput = document.getElementById('toDate');
        const toDateDisplay = document.getElementById('toDateDisplay');

        // Hiển thị giá trị ban đầu (nếu có) theo format Việt Nam
        fromDateDisplay.textContent = formatDateToVN(fromDateInput.value);
        toDateDisplay.textContent = formatDateToVN(toDateInput.value);

        // Xử lý khi người dùng chọn ngày
        fromDateInput.addEventListener('change', function() {
            fromDateDisplay.textContent = formatDateToVN(this.value);
        });

        toDateInput.addEventListener('change', function() {
            toDateDisplay.textContent = formatDateToVN(this.value);
        });
    </script>
</body>
</html>