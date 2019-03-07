<%@ page import="java.net.URLDecoder" %><%--
  Created by IntelliJ IDEA.
  User: alexthor
  Date: 14.10.17
  Time: 1:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <link rel="stylesheet" href="../css/w3.css"/>
        <jsp:useBean id="productBean" type="Beans.ProductPreviewBean" scope="request"/>
        <jsp:useBean id="interfaceBean" type="Beans.InterfaceBean" scope="request"/>
        <jsp:useBean id="user" type="Beans.UserBean" scope="session"/>
    </head>
    <body>
        <div class="w3-bar" style="width:100%">
            <div class="w3-cell-row w3-padding">
                <div class="w3-cell w3-mobile w3-cell-middle" style="width:25%">
                    <img src="${productBean.imageUrl}"
                         alt="${productBean.title}" style="width:95%"/>
                </div>
                <div class="w3-container w3-cell w3-mobile" style="width:55%">
                    <ul class="w3-ul">
                        <li class="w3-large">${productBean.title}</li>
                        <li>${productBean.overview}</li>
                    </ul>
                </div>
                <div class="w3-container w3-cell-middle w3-cell w3-mobile w3-center" style="width:20%">
                    <div class="w3-section">
                        <div>
                            <fmt:setLocale value="${sessionScope.locale}"/>
                            <fmt:formatNumber value="${productBean.price}" type="currency"/>
                        </div>
                        <a href="cartProcess?action=add&product=${productBean.productName}" class="w3-button w3-blue">${interfaceBean.addToCartLabel}</a>
                        <a href="card?product=${productBean.productName}"
                           class="w3-button">${interfaceBean.detailsLabel}</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
