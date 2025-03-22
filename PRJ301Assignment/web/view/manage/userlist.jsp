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
            max-width: 800px;
        }

        h2 {
            text-align: center;
            color: #1a73e8;
            margin-bottom: 30px;
            font-size: 28px;
            font-weight: 600;
        }

        .search-form {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }

        .search-form input[type="text"] {
            padding: 10px;
            width: 300px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }

        .search-form button {
            padding: 10px 20px;
            margin-left: 10px;
            background-color: #1a73e8;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }

        .search-form button:hover {
            background-color: #1557b0;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 15px;
        }

        th, td {
            padding: 15px;
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

        .no-data {
            text-align: center;
            color: #666;
            padding: 30px;
            font-size: 16px;
        }

        .btn {
            padding: 8px 16px;
            border-radius: 6px;
            font-size: 14px;
            text-decoration: none;
            color: white;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn-view {
            background-color: #1a73e8;
        }

        .btn-view:hover {
            background-color: #1557b0;
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            font-size: 16px;
        }

        .back-link a {
            color: #1a73e8;
            text-decoration: none;
        }

        .back-link a:hover {
            text-decoration: underline;
        }

        .pagination {
            text-align: center;
            margin-top: 20px;
        }

        .pagination a {
            color: #1a73e8;
            text-decoration: none;
            padding: 8px 12px;
            margin: 0 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .pagination a:hover {
            background-color: #1a73e8;
            color: white;
        }

        .pagination .current {
            background-color: #1a73e8;
            color: white;
            padding: 8px 12px;
            margin: 0 5px;
            border: 1px solid #1a73e8;
            border-radius: 5px;
        }

        @media (max-width: 768px) {
            table, th, td {
                display: block;
                width: 100%;
            }
            th, td {
                padding: 10px;
            }
            .search-form input[type="text"] {
                width: 100%;
            }
            .search-form button {
                margin-left: 0;
                margin-top: 10px;
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Danh Sách User</h2>

        <!-- Form tìm kiếm -->
        <form class="search-form" method="get" action="<%=request.getContextPath()%>/agenda/userlist">
            <input type="text" name="search" placeholder="Nhập tên để tìm kiếm..." value="${param.search}">
            <button type="submit">Tìm kiếm</button>
        </form>

        <c:choose>
            <c:when test="${not empty users}">
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
                                <td>${user.fullName}</td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/agenda/userworkingdetail?userId=${user.userID}" class="btn btn-view">Xem Chi Tiết</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Phân trang -->
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="<%=request.getContextPath()%>/agenda/userlist?page=${currentPage - 1}&search=${param.search}">Trang trước</a>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <span class="current">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="<%=request.getContextPath()%>/agenda/userlist?page=${i}&search=${param.search}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <a href="<%=request.getContextPath()%>/agenda/userlist?page=${currentPage + 1}&search=${param.search}">Trang sau</a>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <p class="no-data">Hiện tại không có user nào phù hợp với tìm kiếm.</p>
            </c:otherwise>
        </c:choose>
        <div class="back-link">
            <a href="<%=request.getContextPath()%>/home">Quay về Trang Chủ</a>
        </div>
    </div>
</body>
</html>