<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<html>
<head>
    <title>User Page</title>
    <link rel="stylesheet" href="<c:url value="/css/styles.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/normalize.css"/>">
</head>
<body link="red" vlink="#b0cd2e" alink="#b0cd2e">
<h1><span style="color: #b0cd2e; ">${pageAdmin}</span></h1>
<table class="rwd-table">
    <tr>
        <th><s:message code="infoUser"/></th>
    </tr>
    <tr>
            <td data-th="Movie Title"><s:message code="name"/>: ${user.name}. <s:message code="tableNumber"/>: ${user.tableNumber} <a
                    href="http://localhost:8080/projectRestaurant/admins/delete"><s:message code="delete"/></a></td>
            <br>
    </tr>
</table>
<table class="rwd-table">
    <tr>
        <th><s:message code="chekInfo"/></th>
    </tr>
    <tr>
        <td data-th="Movie Title">
            <form action=http://localhost:8080/projectRestaurant/admins/ready>
                <c:forEach var="order" items="${orders}">
                    <option value="${order}">${orderNumber}${order}</option>
                    <br>
                </c:forEach>
                <p>${sum} <s:message code="cost"/></p>
                <s:message code="commentaries"/> ${comment}
                <p>
                    <input type="submit" value=<s:message code="readyOrder"/>>
                </p>
                ${errorCookingCheked}
            </form>
        </td>
    </tr>
</table>
<a href="ServletRestaurant?command=newMenuCreat">${newMenu}</a>
<table class="rwd-table">
    <tr>
        <th><s:message code="actionWithOrder"/></th>
    </tr>
    <tr>
        <td data-th="Movie Title">
            <form action=http://localhost:8080/projectRestaurant/admins/discount>
                <input style="width: 100%" type="submit" value=<s:message code="discount"/>>
            </form>
            <form action=http://localhost:8080/projectRestaurant/admins/allowance>
                <input style="width:100%" type="submit" value=<s:message code="allowance"/>>
            </form>
            <form action=http://localhost:8080/projectRestaurant/admins/count>
                <input style="width:100%" type="submit" value=<s:message code="count"/>>
                ${errorCookingCheked}
            </form>
        </td>
    </tr>
</table>
<table class="rwd-table">
    <tr>
        <td>
            <a href="http://localhost:8080/projectRestaurant/admins/moreInfo"><s:message code="more"/></a>
            <a href="http://localhost:8080/projectRestaurant/logout"><s:message code="logout"/></a>
        </td>
    </tr>
</table>

</body>
</html>