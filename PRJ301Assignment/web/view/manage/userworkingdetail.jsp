<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Tình Hình Lao Động Của ${userWorkingDetail.user.fullName}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; font-family: 'Segoe UI', Arial, sans-serif; }
        .navbar-custom { background-color: #007bff; }
        .navbar-custom .navbar-brand, .navbar-custom .nav-link { color: white; }
        .container-custom { background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); margin-top: 20px; margin-bottom: 20px; }
        h2, h3 { color: #007bff; font-weight: 600; margin-bottom: 20px; }
        .btn-custom { padding: 8px 16px; font-size: 14px; }
        .btn-primary-custom { background-color: #007bff; border-color: #007bff; }
        .btn-primary-custom:hover { background-color: #0056b3; border-color: #0056b3; }
        .timeline { position: relative; max-width: 100%; margin: 0 auto; }
        .timeline::after { content: ''; position: absolute; width: 6px; background-color: #ddd; top: 0; bottom: 0; left: 50%; margin-left: -3px; }
        .timeline-item { padding: 10px 40px; position: relative; background-color: inherit; width: 50%; box-sizing: border-box; }
        .timeline-item::after { content: ''; position: absolute; width: 25px; height: 25px; right: -12.5px; background-color: white; border: 4px solid; top: 15px; border-radius: 50%; z-index: 1; }
        .left { left: 0; }
        .right { left: 50%; }
        .left::before { content: " "; height: 0; position: absolute; top: 22px; width: 0; z-index: 1; right: 30px; border: medium solid white; border-width: 10px 0 10px 10px; border-color: transparent transparent transparent white; }
        .right::before { content: " "; height: 0; position: absolute; top: 22px; width: 0; z-index: 1; left: 30px; border: medium solid white; border-width: 10px 10px 10px 0; border-color: transparent white transparent transparent; }
        .right::after { left: -12.5px; }
        .content { padding: 20px 30px; background-color: white; position: relative; border-radius: 6px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); }
        .approved::after { border-color: #28a745; }
        .not-approved::after { border-color: #ff9800; }
        @media (max-width: 768px) {
            .timeline::after { left: 31px; }
            .timeline-item { width: 100%; padding-left: 70px; padding-right: 25px; }
            .timeline-item::before { left: 60px; border: medium solid white; border-width: 10px 10px 10px 0; border-color: transparent white transparent transparent; }
            .left::after, .right::after { left: 18.5px; }
            .right { left: 0%; }
        }
        footer { text-align: center; padding: 10px; background-color: #007bff; color: white; position: fixed; bottom: 0; width: 100%; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-custom">
        <div class="container-fluid">
            <a class="navbar-brand" href="<%=request.getContextPath()%>/home">Quản Lý Nghỉ Phép</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/logout">Đăng Xuất</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container container-custom">
        <h2 class="text-center">Chi Tiết Tình Hình Lao Động Của ${userWorkingDetail.user.fullName}</h2>
        <c:choose>
            <c:when test="${not empty userWorkingDetail.leaveRequests}">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-dark">
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
                </div>
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="<%=request.getContextPath()%>/agenda/userworkingdetail?userId=${userWorkingDetail.user.userID}&page=${currentPage - 1}">Trang trước</a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="<%=request.getContextPath()%>/agenda/userworkingdetail?userId=${userWorkingDetail.user.userID}&page=${i}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="<%=request.getContextPath()%>/agenda/userworkingdetail?userId=${userWorkingDetail.user.userID}&page=${currentPage + 1}">Trang sau</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
                <div class="timeline-section mt-5">
                    <h3 class="text-center">Timeline Đơn Xin Nghỉ Phép</h3>
                    <div class="timeline">
                        <c:forEach var="request" items="${userWorkingDetail.leaveRequests}" varStatus="loop">
                            <c:set var="statusClass" value="${request.leaveStatus != null && request.leaveStatus.statusName == 'Approved' ? 'approved' : 'not-approved'}" />
                            <div class="timeline-item ${loop.index % 2 == 0 ? 'left' : 'right'} ${statusClass}">
                                <div class="content">
                                    <h4>Ngày Bắt Đầu: <fmt:formatDate value="${request.fromDate}" pattern="dd/MM/yyyy"/></h4>
                                    <p>Lý do: ${request.reason}</p>
                                    <p>Trạng thái: ${request.leaveStatus != null ? request.leaveStatus.statusName : 'Chưa xác định'}</p>
                                </div>
                            </div>
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
                <p class="text-center text-muted">Hiện tại không có đơn xin nghỉ phép nào cho user này.</p>
            </c:otherwise>
        </c:choose>
        <div class="text-center mt-3">
            <a href="<%=request.getContextPath()%>/agenda/userlist" class="btn btn-primary-custom btn-custom">Quay về Danh Sách User</a>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 Quản Lý Nghỉ Phép. All rights reserved.</p>
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>