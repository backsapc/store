<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: alexthor
  Date: 25.10.17
  Time: 5:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../css/w3.css"/>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="InterfaceBundle" var="rofle"/>
    <title><fmt:message key="AuthError" bundle="${rofle}"/></title>
</head>
<body>
<div class="w3-display-middle w3-card" style="margin: 0 auto; width:40%;">
    <div class="w3-container w3-blue w3-center w3-margin-bottom">
        <h2><fmt:message key="AuthError" bundle="${rofle}"/></h2>
    </div>
    <div class="w3-container">
        <fmt:message key="ErrorLoginMessage" bundle="${rofle}"/>
    </div>
    <div class="w3-container w3-center w3-margin">
        <a href="login" class="w3-button w3-blue">
            <fmt:message key="TryAgain" bundle="${rofle}"/> </a>
        <a href="products" class="w3-button w3-grey">
            <fmt:message key="OnMain" bundle="${rofle}"/></a>
    </div>
</div>
</body>
</html>
