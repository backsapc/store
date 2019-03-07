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
        <jsp:useBean id="interfaceBean" type="Beans.InterfaceBean" scope="request"/>
        <jsp:useBean id="cart" type="Beans.CartBean" scope="session"/>
        <jsp:useBean id="user" type="Beans.UserBean" scope="session"/>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>Surface store - ${interfaceBean.selectedCategory}</title>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div class="w3-container w3-margin-top">
            <div class="w3-row">
                <div class="w3-container w3-cell">${interfaceBean.categoryLabel}:</div>
                <div class="w3-container w3-cell w3-dropdown-hover">
                    ${interfaceBean.selectedCategory}
                    <div class="w3-dropdown-content w3-bar-block w3-border">
                        <a href="products?category=all"
                           class="w3-bar-item w3-button">${interfaceBean.allLabel}</a>
                        <a href="products?category=desktops"
                           class="w3-bar-item w3-button">${interfaceBean.desktopsLabel}</a>
                        <a href="products?category=tablets"
                           class="w3-bar-item w3-button">${interfaceBean.tabletsLabel}</a>
                        <a href="products?category=laptops"
                           class="w3-bar-item w3-button">${interfaceBean.laptopsLabel}</a>
                    </div>
                </div>
            </div>
            <hr>
            <p class='w3-xlarge w3-margin-left'>${interfaceBean.productListLabel}</p>
            <ul class="w3-ul w3-card-2 w3-center" style="width: 90%; margin: 0 auto;">
                <c:forEach items="${products}" var="product">
                    <li>
                        <jsp:include page="/preview">
                            <jsp:param name="product" value="${product}"/>
                            <jsp:param name="page" value="productPreview.jsp"/>
                            <jsp:param name="isCart" value="0"/>
                        </jsp:include>
                    </li>
                </c:forEach>
            </ul>
        </div>
    <hr>
    </body>
</html>
