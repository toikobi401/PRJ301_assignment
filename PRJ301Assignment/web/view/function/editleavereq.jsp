<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Leave Request</title>
</head>
<body>
    <h2>Edit Leave Request</h2>
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>
    <form action="EditLeaveRequestServlet" method="post">
        <input type="hidden" name="requestId" value="${request.requestID}">
        <input type="hidden" name="userId" value="${request.userID}"> <!-- Giữ nguyên UserID -->
        <label>User ID:</label>
        <input type="text" value="${request.userID}" disabled><br> <!-- Hiển thị nhưng không cho sửa -->
        <label>Request Date:</label>
        <input type="date" name="requestDate" value="${request.requestDate}" required><br>
        <label>Reason:</label>
        <textarea name="reason" required>${request.reason}</textarea><br>
        <label>Status:</label>
        <select name="statusId">
            <option value="0" ${request.statusID == 0 ? 'selected' : ''}>Pending</option>
            <option value="1" ${request.statusID == 1 ? 'selected' : ''}>Approved</option>
            <option value="2" ${request.statusID == 2 ? 'selected' : ''}>Rejected</option>
        </select><br>
        <input type="submit" value="Update">
    </form>
    <a href="YourLeaveRequestServlet">Back to List</a>
</body>
</html>