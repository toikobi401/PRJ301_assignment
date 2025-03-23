<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Tình Hình Lao Động Của ${userWorkingDetail.user.fullName}</title>
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
            max-width: 1200px;
        }

        h2 {
            text-align: center;
            color: #1a73e8;
            margin-bottom: 30px;
            font-size: 28px;
            font-weight: 600;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 15px;
            margin-bottom: 30px;
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

        /* CSS cho Timeline */
        .timeline-section {
            margin-top: 40px;
        }

        .timeline-section h3 {
            text-align: center;
            color: #1a73e8;
            font-size: 24px;
            margin-bottom: 20px;
        }

        .timeline {
            position: relative;
            max-width: 100%;
            margin: 0 auto;
        }

        .timeline::after {
            content: '';
            position: absolute;
            width: 6px;
            background-color: #ddd;
            top: 0;
            bottom: 0;
            left: 50%;
            margin-left: -3px;
        }

        .timeline-item {
            padding: 10px 40px;
            position: relative;
            background-color: inherit;
            width: 50%;
            box-sizing: border-box;
        }

        .timeline-item::after {
            content: '';
            position: absolute;
            width: 25px;
            height: 25px;
            right: -12.5px;
            background-color: white;
            border: 4px solid;
            top: 15px;
            border-radius: 50%;
            z-index: 1;
        }

        .left {
            left: 0;
        }

        .right {
            left: 50%;
        }

        .left::before {
            content: " ";
            height: 0;
            position: absolute;
            top: 22px;
            width: 0;
            z-index: 1;
            right: 30px;
            border: medium solid white;
            border-width: 10px 0 10px 10px;
            border-color: transparent transparent transparent white;
        }

        .right::before {
            content: " ";
            height: 0;
            position: absolute;
            top: 22px;
            width: 0;
            z-index: 1;
            left: 30px;
            border: medium solid white;
            border-width: 10px 10px 10px 0;
            border-color: transparent white transparent transparent;
        }

        .right::after {
            left: -12.5px;
        }

        .content {
            padding: 20px 30px;
            background-color: white;
            position: relative;
            border-radius: 6px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .approved::after {
            border-color: #28a745;
        }

        .not-approved::after {
            border-color: #ff9800;
        }

        .content h4 {
            margin: 0 0 10px 0;
            font-size: 16px;
            color: #333;
        }

        .content p {
            margin: 0;
            font-size: 14px;
            color: #666;
        }

        @media (max-width: 768px) {
            table, th, td {
                display: block;
                width: 100%;
            }
            th, td {
                padding: 10px;
            }
            .timeline::after {
                left: 31px;
            }
            .timeline-item {
                width: 100%;
                padding-left: 70px;
                padding-right: 25px;
            }
            .timeline-item::before {
                left: 60px;
                border: medium solid white;
                border-width: 10px 10px 10px 0;
                border-color: transparent white transparent transparent;
            }
            .left::after, .right::after {
                left: 18.5px;
            }
            .right {
                left: 0%;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Chi Tiết Tình Hình Lao Động Của ${userWorkingDetail.user.fullName}</h2>
        <c:choose>
            <c:when test="${not empty userWorkingDetail.leaveRequests}">
                <!-- Bảng hiển thị danh sách đơn xin nghỉ phép -->
                <table>
                    <thead>
                        <tr>
                            <th>Từ Ngày</th>
                            <th>Đến Ngày</th>
                            <th>Lý Do</th>
                            <th>Trạng Thái</th>
                            <th>Mô Tả</th>
                            <th>Người Phê Duyệt</th>
                            <th>Ngày Tạo</th>
                            <th>Ngày Cập Nhật</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="request" items="${userWorkingDetail.leaveRequests}">
                            <tr>
                                <td><fmt:formatDate value="${request.fromDate}" pattern="dd/MM/yyyy"/></td>
                                <td><fmt:formatDate value="${request.toDate}" pattern="dd/MM/yyyy"/></td>
                                <td>${request.reason}</td>
                                <td>${request.leaveStatus != null ? request.leaveStatus.statusName : 'Chưa xác định'}</td>
                                <td>${request.leaveStatus != null ? request.leaveStatus.description : 'N/A'}</td>
                                <td>${request.approvedBy != null ? request.approvedBy : 'Chưa phê duyệt'}</td>
                                <td><fmt:formatDate value="${request.createAt}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                                <td><fmt:formatDate value="${request.updateAt}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Phân trang -->
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="<%=request.getContextPath()%>/agenda/userworkingdetail?userId=${userWorkingDetail.user.userID}&page=${currentPage - 1}">Trang trước</a>
                    </c:if>
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <span class="current">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="<%=request.getContextPath()%>/agenda/userworkingdetail?userId=${userWorkingDetail.user.userID}&page=${i}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${currentPage < totalPages}">
                        <a href="<%=request.getContextPath()%>/agenda/userworkingdetail?userId=${userWorkingDetail.user.userID}&page=${currentPage + 1}">Trang sau</a>
                    </c:if>
                </div>

                <!-- Timeline -->
                <div class="timeline-section">
                    <h3>Timeline Đơn Xin Nghỉ Phép</h3>
                    <div class="timeline">
                        <c:forEach var="request" items="${userWorkingDetail.leaveRequests}" varStatus="loop">
                            <c:set var="statusClass" value="${request.leaveStatus != null && request.leaveStatus.statusName == 'Approved' ? 'approved' : 'not-approved'}" />
                            
                            <!-- Ngày bắt đầu -->
                            <div class="timeline-item ${loop.index % 2 == 0 ? 'left' : 'right'} ${statusClass}">
                                <div class="content">
                                    <h4>Ngày Bắt Đầu: <fmt:formatDate value="${request.fromDate}" pattern="dd/MM/yyyy"/></h4>
                                    <p>Lý do: ${request.reason}</p>
                                    <p>Trạng thái: ${request.leaveStatus != null ? request.leaveStatus.statusName : 'Chưa xác định'}</p>
                                </div>
                            </div>
                            <!-- Ngày kết thúc -->
                            <div class="timeline-item ${loop.index % 2 == 0 ? 'right' : 'left'} ${statusClass}">
                                <div class="content">
                                    <h4>Ngày Kết Thúc: <fmt:formatDate value="${request.toDate}" pattern="dd/MM/yyyy"/></h4>
                                    <p>Lý do: ${request.reason}</p>
                                    <p>Trạng thái: ${request.leaveStatus != null ? request.leaveStatus.statusName : 'Chưa xác định'}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <p class="no-data">Hiện tại không có đơn xin nghỉ phép nào cho user này.</p>
            </c:otherwise>
        </c:choose>
        <div class="back-link">
            <a href="<%=request.getContextPath()%>/agenda/userlist">Quay về Danh Sách User</a>
        </div>
    </div>
</body>
</html>