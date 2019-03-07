<%@ page import="Beans.ProductPreviewBean" %>
<%@ page import="java.net.URLEncoder" %><%--
  Created by IntelliJ IDEA.
  User: alexthor
  Date: 14.10.17
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
    <head>
        <link rel="stylesheet" href="../css/w3.css">
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <jsp:useBean id="interfaceBean" type="Beans.InterfaceBean" scope="request"/>
        <jsp:useBean id="cart" type="Beans.CartBean" scope="session"/>
        <jsp:useBean id="user" type="Beans.UserBean" scope="session"/>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <fmt:setLocale value="${sessionScope.locale}"/>
        <fmt:setBundle basename="InterfaceBundle" var="rofle"/>
        <title>Surface store - ${user.name}</title>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div class="w3-container w3-content" style="max-width:1400px;margin-top:15px">
            <!-- Left Column -->
            <div class="w3-half">
                <!-- History -->
                <div class=" w3-white w3-padding" style="margin:0 auto;">
                    <div class="w3-row w3-padding">
                        <div class="w3-large w3-border-bottom">
                            <fmt:message key="CheckoutDetails" bundle="${rofle}"/>
                        </div>
                    </div>
                    <table class="w3-table">
                        <tr>
                            <td><fmt:message key="OrderNumber" bundle="${rofle}"/></td>
                            <td>${requestScope.get('order').id}</td>
                        </tr>
                        <tr>
                            <td><fmt:message key="Date" bundle="${rofle}"/></td>
                            <td>
                                <fmt:formatDate value="${requestScope.get('order').fullDate}" type="both"/>
                            </td>
                        </tr>
                        <tr>
                            <td><fmt:message key="Total" bundle="${rofle}"/></td>
                            <td><fmt:formatNumber value="${requestScope.get('order').total}"
                                                  type="currency"
                                                  currencyCode="${requestScope.get('order').currency}"/>
                            </td>
                        </tr>
                        <tr>
                            <td><fmt:message key="Address" bundle="${rofle}"/></td>
                            <td>${requestScope.get('order').address}</td>
                        </tr>
                        <tr>
                            <td><fmt:message key="DeliveredBy" bundle="${rofle}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${requestScope.get('order').deliveredby eq 'courier'}">
                                        <fmt:message key="Courier" bundle="${rofle}"/>
                                    </c:when>
                                    <c:otherwise>
                                        ${requestScope.get('order').deliveredby}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </table>
                </div>
                <!-- End Left Column -->
            </div>
            <!-- Right Column -->
            <div class="w3-half">
                <!-- History -->
                <div class=" w3-white w3-padding" style="margin:0 auto;">
                    <div class="w3-row w3-padding">
                        <div class="w3-large w3-border-bottom">
                            <fmt:message key="ProductsListLabel" bundle="${rofle}"/>
                        </div>
                    </div>
                    <div class="w3-container">
                        <table class="w3-table w3-striped">
                            <tr>
                                <th><fmt:message key="Product" bundle="${rofle}"/></th>
                                <th><fmt:message key="Amount" bundle="${rofle}"/></th>
                                <th><fmt:message key="Price" bundle="${rofle}"/></th>
                            </tr>
                            <c:forEach items="${requestScope.get('orderDetails')}" var="orderDetail">
                                <tr>
                                    <td>
                                            ${orderDetail.productName}
                                    </td>
                                    <td>
                                            ${orderDetail.amount}
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${orderDetail.price}"
                                                          type="currency"
                                                          currencyCode="${requestScope.get('order').currency}"
                                        />
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
                <!-- End Right Column -->
            </div>
            <!-- End Page Container -->
        </div>
    <hr>
    </body>
</html>
