<%--
  Created by IntelliJ IDEA.
  User: alexthor
  Date: 22.10.17
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../css/w3.css">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:useBean id="interfaceBean" type="Beans.InterfaceBean" scope="request"/>
    <jsp:useBean id="cart" type="Beans.CartBean" scope="session"/>
    <jsp:useBean id="user" type="Beans.UserBean" scope="session"/>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <fmt:setBundle basename="InterfaceBundle" var="rofle"/>
    <title>Surface store</title>
</head>
<body>
<%@include file="header.jsp"%>
        <div class="w3-container">
            <div class="w3-row">
                <p class='w3-xlarge w3-margin-left'>${interfaceBean.cartLabel}</p>
            </div>
            <c:choose>
                <c:when test="${cart.itemsAmount == 0}">
                    <div class="w3-row w3-padding">
                        <div class="w3-large">${interfaceBean.emptyCartLabel}</div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div>
                        <div class="w3-third">
                            <div class="w3-row w3-padding">
                                <div class='w3-large'>${interfaceBean.summaryLabel}</div>
                                <hr>
                                <table class="w3-table w3-striped w3-padding">
                                    <tr>
                                        <td>${interfaceBean.itemsLabel}</td>
                                        <td>${cart.itemsAmount}</td>
                                    </tr>
                                    <tr>
                                        <td>${interfaceBean.totalLabel}</td>
                                        <td>
                                            <fmt:setLocale value="${sessionScope.locale}"/>
                                            <fmt:formatNumber value="${cart.totalPrice}" type="currency"/>
                                        </td>
                                    </tr>
                                </table>
                                <hr>
                            </div>
                            <div class="w3-row w3-padding">
                                <a href="checkout" class="w3-button w3-blue w3-right w3-margin-top">${interfaceBean.checkoutLabel}</a>
                            </div>
                        </div>
                        <div class="w3-twothird">
                            <div class="w3-row w3-padding">
                                <div class='w3-large'>&nbsp;</div>
                                <hr>
                            </div>
                            <ul class="w3-ul w3-card-2 w3-center" style="margin: 0 auto;">
                                <c:forEach items="${cart.itemsById}" var="product">
                                    <li>
                                        <jsp:include page="/preview">
                                            <jsp:param name="product" value="${product.key}"/>
                                            <jsp:param name="page" value="productPreviewCart.jsp"/>
                                            <jsp:param name="isCart" value="1"/>
                                        </jsp:include>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <hr>
    </body>
</html>