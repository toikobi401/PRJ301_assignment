<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Tình Hình Lao Động</title>
    <script src="https://cdn.knightlab.com/libs/timeline3/latest/js/timeline.js"></script>
    <link rel="stylesheet" href="https://cdn.knightlab.com/libs/timeline3/latest/css/timeline.css">
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
            text-align: center;
        }

        h2 {
            color: #1a73e8;
            margin-bottom: 20px;
            font-size: 28px;
            font-weight: 600;
        }

        .no-data {
            text-align: center;
            color: #666;
            padding: 30px;
            font-size: 16px;
        }

        .approved { background-color: #28a745; color: white; }
        .pending { background-color: #ffc107; color: black; }

        #timeline {
            margin-top: 20px;
            border-radius: 6px;
            overflow: hidden;
        }

        @media (max-width: 768px) {
            .container {
                padding: 20px;
            }

            h2 {
                font-size: 24px;
            }

            .no-data {
                font-size: 14px;
                padding: 20px;
            }

            #timeline {
                height: 300px !important; /* Điều chỉnh chiều cao timeline trên mobile */
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Chi Tiết Tình Hình Lao Động (${userWorkingDetail.user.fullName})</h2>
        <c:choose>
            <c:when test="${not empty userWorkingDetail.leaveRequests}">
                <div id="timeline"></div>
                <script>
                    var items = [
                        <c:forEach var="request" items="${userWorkingDetail.leaveRequests}" varStatus="status">
                            {
                                start: '<fmt:formatDate value="${request.fromDate}" pattern="yyyy-MM-dd"/>',
                                end: '<fmt:formatDate value="${request.toDate}" pattern="yyyy-MM-dd"/>',
                                content: '${request.reason}',
                                className: '${request.statusID == 1 ? "approved" : "pending"}'
                            }<c:if test="${!status.last}">,</c:if>
                        </c:forEach>
                    ];
                    console.log("Dữ liệu timeline:", items); // Debug: Kiểm tra dữ liệu truyền vào timeline
                    var options = {
                        height: '400px',
                        width: '100%',
                        zoomable: true,
                        moveable: true
                    };
                    var timeline = new TL.Timeline('timeline', { events: items }, options);
                </script>
            </c:when>
            <c:otherwise>
                <p class="no-data">Hiện tại không có đơn xin nghỉ phép nào cho user này.</p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>