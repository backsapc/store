<%--
  Created by IntelliJ IDEA.
  User: alexthor
  Date: 22.10.17
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<html >
<head>
    <link rel="stylesheet" href="../css/w3.css"/>
    <jsp:useBean id="productBean" type="Beans.ProductPreviewBean" scope="request"/>
    <jsp:useBean id="interfaceBean" type="Beans.InterfaceBean" scope="request"/>
    <jsp:useBean id="cartItemBean" type="Beans.CartItemBean" scope="request"/>
    <jsp:useBean id="user" type="Beans.UserBean" scope="session"/>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
</head>
<body>
<div class="w3-bar" style="width:100%">
    <div class="w3-cell-row w3-padding">
        <div class="w3-cell w3-mobile w3-cell-middle" style="width:15%">
            <img src="${productBean.imageUrl}"
                 alt="${productBean.productName}" style="width:95%"/>
        </div>
        <div class="w3-container w3-cell-middle w3-cell w3-mobile w3-center" style="width:25%">
            <div class="w3-large">${productBean.title}</div>
        </div>
        <div class="w3-container w3-cell-middle w3-cell w3-mobile w3-center" style="width:55%">
            <div class="w3-cell-row">
                <div class="w3-container w3-cell-middle w3-cell">
                    <div class="w3-row">
                        <div>${interfaceBean.itemsLabel}</div>
                    </div>
                    <div class="w3-row w3-bar">
                        <a href="cartProcess?action=remove&product=${cartItemBean.product}" class="w3-bar-item w3-button">-</a>
                        <div class="w3-bar-item">${cartItemBean.numberOrdered}</div>
                        <a href="cartProcess?action=add&product=${cartItemBean.product}" class="w3-bar-item w3-button">+</a>
                    </div>
                </div>
                <div class="w3-container w3-cell-middle w3-cell">
                    <div class="w3-row">
                        <div>${interfaceBean.priceLabel}</div>
                    </div>
                    <div class="w3-row w3-bar">
                        <div class="w3-bar-item">
                            <fmt:setLocale value="${sessionScope.locale}"/>
                            <fmt:formatNumber value="${productBean.price}" type="currency"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="w3-container w3-cell-middle w3-cell w3-mobile w3-center" style="width:5%">
            <div class="w3-section">
                <a href="cartProcess?action=throw&product=${cartItemBean.product}" class="w3-button">×</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>

