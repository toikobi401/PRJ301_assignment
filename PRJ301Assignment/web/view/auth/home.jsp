<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Chủ</title>
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

        p {
            font-size: 16px;
            color: #333;
            margin-bottom: 30px;
        }

        .links {
            display: flex;
            flex-direction: column;
            gap: 15px;
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

        .btn-logout {
            background-color: #e63946;
        }

        .btn-logout:hover {
            background-color: #d00000;
        }

        @media (max-width: 768px) {
            .container {
                padding: 20px;
            }

            .btn {
                font-size: 14px;
                padding: 8px 16px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Chào mừng đến với Trang Chủ</h2>
        <c:if test="${sessionScope.user != null}">
            <p>Xin chào, ${sessionScope.user.fullName}!</p>
            <div class="links">
                <!-- Liên kết chung cho tất cả user -->
                <a href="<%=request.getContextPath()%>/LeaveRequest/list" class="btn btn-action">Xem Danh Sách Đơn Xin Nghỉ Của Bạn</a>
                <a href="<%=request.getContextPath()%>/LeaveRequest/create" class="btn btn-action">Tạo Đơn Xin Nghỉ Mới</a>

                <!-- Liên kết chỉ hiển thị cho Admin hoặc Manager (RoleID = 1 hoặc 2) -->
                <c:set var="isAdminOrManager" value="false" />
                <c:if test="${not empty sessionScope.user.roles}">
                    <c:forEach var="role" items="${sessionScope.user.roles}">
                        <c:if test="${role.roleID == 1 || role.roleID == 2}">
                            <c:set var="isAdminOrManager" value="true" />
                        </c:if>
                    </c:forEach>
                </c:if>
                <c:if test="${isAdminOrManager}">
                    <a href="<%=request.getContextPath()%>/LeaveRequest/manage" class="btn btn-action">Quản Lý Đơn Xin Nghỉ</a>
                </c:if>

                <!-- Liên kết chỉ hiển thị cho Admin (RoleID = 1) -->
                <c:set var="isAdmin" value="false" />
                <c:if test="${not empty sessionScope.user.roles}">
                    <c:forEach var="role" items="${sessionScope.user.roles}">
                        <c:if test="${role.roleID == 1}">
                            <c:set var="isAdmin" value="true" />
                        </c:if>
                    </c:forEach>
                </c:if>
                <c:if test="${isAdmin}">
                    <a href="<%=request.getContextPath()%>/agenda/userlist" class="btn btn-action">Xem Tình Hình Lao Động</a>
                </c:if>

                <!-- Liên kết đăng xuất -->
                <a href="<%=request.getContextPath()%>/logout" class="btn btn-logout">Đăng Xuất</a>
            </div>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <p>Vui lòng <a href="<%=request.getContextPath()%>/login">đăng nhập</a> để tiếp tục.</p>
        </c:if>
    </div>
</body>
</html>