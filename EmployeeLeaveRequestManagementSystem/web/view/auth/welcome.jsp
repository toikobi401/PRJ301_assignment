<%-- 
    Document   : welcome
    Created on : Feb 19, 2025, 10:48:47 AM
    Author     : sonnt-local
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello ${sessionScope.user.displayname}</h1>
        <c:if test="${sessionScope.user.employee.manager ne null}">
            you need to report to: ${sessionScope.user.employee.manager.name} <br/>
        </c:if>
        Report to you:
        <c:forEach items="${sessionScope.user.employee.staffs}" var="s">
            ${s.name} <br/>
        </c:forEach>
    </body>
</html>
