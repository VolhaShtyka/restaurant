<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<html>
<head>
    <title>Admin Page</title>
    <link rel="stylesheet" href="<c:url value="/css/styles.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/normalize.css"/>">
</head>
<body>
<h1><span style="color: #b0cd2e; "><s:message code="pageAdmin"/></span></h1>
<h1><span style="color: #b0cd2e; "><s:message code="hello"/>${nameAdmin}</span></h1>
<h3 style="color: #b0cd2e; "><s:message code="info"/></h3>
<table class="rwd-table" border="1">
    <tr>
        <th>${name}
            <form action=http://localhost:8080/projectRestaurant/admins/adminFilter>
                <button name="command" value="adminFilter">
                    <img src=http://localhost:8080/projectRestaurant/css/up.png style="vertical-align: middle">
                    <input type="hidden" name="upPrice" value="nameUp"/>
                </button>
            </form>
            <form action=http://localhost:8080/projectRestaurant/admins/adminFilter>
                <button name="command" value="adminFilter">
                    <img src=http://localhost:8080/projectRestaurant/css/down.png style="vertical-align: middle">
                    <input type="hidden" name="upPrice" value="nameDown"/></button>
            </form>
        </th>
        <th>${tableNumber}
            <form action=http://localhost:8080/projectRestaurant/admins/adminFilter>
                <button name="command" value="adminFilter">
                    <img src=http://localhost:8080/projectRestaurant/css/up.png  style="vertical-align: middle">
                    <input type="hidden" name="upPrice" value="tableUp"/></button>
            </form>
            <form action=http://localhost:8080/projectRestaurant/admins/adminFilter>
                <button name="command" value="adminFilter">
                    <img src=http://localhost:8080/projectRestaurant/css/down.png style="vertical-align: middle">
                    <input type="hidden" name="upPrice" value="tableDown"/></button>
            </form>
            </button>
            </form></th>
        <th>${sumOrder}
            <form action=http://localhost:8080/projectRestaurant/admins/adminFilter>
                <button name="command" value="adminFilter">
                    <img src=http://localhost:8080/projectRestaurant/css/up.png  style="vertical-align: middle">
                    <input type="hidden" name="upPrice" value="sumUp"/></button>
            </form>
            <form action=http://localhost:8080/projectRestaurant/admins/adminFilter>
                <button name="command" value="adminFilter">
                    <img src=http://localhost:8080/projectRestaurant/css/down.png style="vertical-align: middle">
                    <input type="hidden" name="upPrice" value="sumDown"/></button>
            </form>
        </th>
        <th><s:message code="more"/></th>
    </tr>


    <c:forEach var="user" items="${users}">
        <c:set var="i" value="${user.id}"/>
        <tr>
            <td>${user.name}</td>
            <td>${user.tableNumber}</td>
            <td>${sum.get(i)}</td>
            <td><a href="http://localhost:8080/projectRestaurant/admins/clientDetails?user=${user.name}"><s:message code="details"/></a></td>
        </tr>
    </c:forEach>
</table>


<table border="1" cellpadding="5" cellspacing="5" bgcolor="#f0f8ff">
    <tr>
        <c:if test="${currentPage != 1}">
            <td>
                <a href="http://localhost:8080/projectRestaurant/admins/admin?currentPage=${currentPage - 1}">Предыдущая
                </a>
            </td>
        </c:if>
        <c:forEach begin="1" end="${numberOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td>
                        ><a href="http://localhost:8080/projectRestaurant/admins/admin?currentPage=${i}">${i}</a>
                    </td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage lt numberOfPages}">
            <td>
                <a href="http://localhost:8080/projectRestaurant/admins/admin?currentPage=${currentPage + 1}">Следующая</a>
            </td>
        </c:if>
    </tr>
</table>


<table>
    <form action=http://localhost:8080/projectRestaurant/admins/filterClients>
        <th style="color: blue; font-size: larger">Отфильтровать по:</th>
        <tr>
            <td>
                Сумме заказа <input name="minPrice" type="number" value=""/>
                <input name="maxPrice" type="number" value=""/>

            </td>
        </tr>


        <tr>
            <td>
                Номеру столика
                <input name="minTableNumber" type="number" value=""/>
                <input name="maxTableNumber" type="number" value=""/>
                <input type="submit" value=<s:message code="show"/>/>
            </td>
        </tr>
    </form>
    <tr>
        <td>
            <form action=http://localhost:8080/projectRestaurant/admins/clearSortAdmin>
                <input type="submit" value=<s:message code="reset"/>/>
            </form>
        </td>
    </tr>
</table>

<a href="http://localhost:8080/projectRestaurant/admins/newMenu"><s:message code="newMenu"/></a>
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