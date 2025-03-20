<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách User</title>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background-color: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
            text-align: center;
        }

        h2 {
            color: #1a73e8;
            margin-bottom: 20px;
            font-size: 28px;
            font-weight: 600;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #e0e0e0;
        }

        th {
            background-color: #1a73e8;
            color: white;
            font-weight: 600;
            text-transform: uppercase;
        }

        tr:hover {
            background-color: #f5f7fa;
        }

        .btn {
            padding: 10px 20px;
            border-radius: 6px;
            font-size: 16px;
            text-decoration: none;
            color: white;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
            display: inline-block;
        }

        .btn-action {
            background-color: #1a73e8;
        }

        .btn-action:hover {
            background-color: #1557b0;
        }

        @media (max-width: 768px) {
            .container {
                padding: 20px;
            }

            .btn {
                font-size: 14px;
                padding: 8px 16px;
            }

            table, th, td {
                display: block;
                width: 100%;
            }

            th, td {
                padding: 10px;
                text-align: center;
            }

            th {
                display: none; /* Ẩn tiêu đề trên mobile */
            }

            td {
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: 1px solid #e0e0e0;
            }

            td:before {
                content: attr(data-label);
                font-weight: bold;
                color: #34495e;
                width: 50%;
                text-align: left;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Danh Sách User</h2>
        <table>
            <thead>
                <tr>
                    <th>Tên User</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td data-label="Tên User">${user.fullName}</td>
                        <td data-label="Hành Động">
                            <a href="<%=request.getContextPath()%>/agenda/userworkingdetail?userId=${user.userID}" class="btn btn-action">Xem Chi Tiết</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>