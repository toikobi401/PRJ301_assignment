<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Leave Requests</title>
</head>
<body>
    <h2>Your Leave Requests</h2>
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>
    <table border="1">
        <tr>
            <th>Request ID</th>
            <th>Request Date</th>
            <th>Reason</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="request" items="${leaveRequests}">
            <tr>
                <td>${request.requestID}</td>
                <td>${request.requestDate}</td>
                <td>${request.reason}</td>
                <td>
                    <c:choose>
                        <c:when test="${request.statusID == 0}">Pending</c:when>
                        <c:when test="${request.statusID == 1}">Approved</c:when>
                        <c:when test="${request.statusID == 2}">Rejected</c:when>
                    </c:choose>
                </td>
                <td>
                    <a href="EditLeaveRequestServlet?requestId=${request.requestID}">Edit</a> |
                    <a href="DeleteLeaveRequestServlet?requestId=${request.requestID}" 
                       onclick="return confirm('Are you sure?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="createLeave.jsp">Create New Request</a>
</body>
</html>