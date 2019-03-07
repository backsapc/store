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
    <title><fmt:message key="Login" bundle="${rofle}"/></title>
</head>
<body>
<div class="w3-display-middle w3-card" style="margin: 0 auto; width:40%;">
    <div class="w3-container w3-blue w3-center w3-margin-bottom">
        <h2><fmt:message key="Login" bundle="${rofle}"/></h2>
    </div>
    <form action="j_security_check" method="post" name="loginForm" class="w3-container">
        <label class="w3-text-blue"><b><fmt:message key="Username" bundle="${rofle}"/></b></label>
        <input class="w3-input w3-border" type="text" name="j_username">

        <label class="w3-text-blue"><b><fmt:message key="Password" bundle="${rofle}"/></b></label>
        <input class="w3-input w3-border" type="password" name="j_password">
        <input class="w3-button w3-blue w3-right w3-margin" type="submit" value="Log in"/>
    </form>
</div>
</body>
</html>
