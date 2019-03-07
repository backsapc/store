<%--
  Created by IntelliJ IDEA.
  User: alexthor
  Date: 22.10.17
  Time: 16:14
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
    <title>Surface store</title>
</head>
<body>
    <%@include file="header.jsp"%>
    <%=request.getAttribute("page")%>
    <hr>
    <!--
    <script type="text/javascript">
        <!%@include file="../scripts/interaction.js"%>
    </script> -->
</body>
</html>