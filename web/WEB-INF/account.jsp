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
            <div class="w3-row">

                <div class="w3-cell w3-cell-middle">
                    <img src="img/cat.jpg" class="w3-circle" style="height:50px;" alt="Avatar">
                </div>
                <div class="w3-cell w3-cell-middle w3-container">
                    <span class="w3-large">
                        <fmt:message key="Profile" bundle="${rofle}"/>:
                    </span>
                    <span>
                        ${user.name}
                    </span>
                </div>
                <div class="w3-cell w3-cell-middle">
                    <div class="w3-row">
                        <span class="w3-container w3-cell"><fmt:message key="DefaultTab" bundle="${rofle}"/>:</span>
                        <div class="w3-container w3-cell w3-dropdown-hover">
                            <fmt:message key="${user.defaultTab}Label" bundle="${rofle}"/>
                            <div class="w3-dropdown-content w3-bar-block w3-border">
                                <a href="account?defaultTab=Overview"
                                   class="w3-bar-item w3-button">
                                    <fmt:message key="OverviewLabel" bundle="${rofle}"/></a>
                                <a href="account?defaultTab=TechSpecs"
                                   class="w3-bar-item w3-button">
                                    <fmt:message key="TechSpecsLabel" bundle="${rofle}"/></a>
                                <a href="account?defaultTab=Reviews"
                                   class="w3-bar-item w3-button">
                                    <fmt:message key="ReviewsLabel" bundle="${rofle}"/></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="w3-cell w3-right w3-cell-middle">
                    <div id="dateTime"></div>
                </div>
                <hr/>
            </div>
            <!-- The Grid -->
            <div class="w3-row">
                <!-- Left Column -->
                <div class="w3-half w3-container">
                    <!-- Comments -->
                    <div class="w3-row w3-padding w3-border-bottom">
                        <div class="w3-large w3-center">
                            <fmt:message key="Comments" bundle="${rofle}"/>
                        </div>
                    </div>
                    <div class="w3-row w3-padding">
                        <div id="respond">
                            <!-- тут нужно обратить внимание также на атрибуты name -->
                            <form id="commentForm" class="w3-container w3-center">
                                <div class="w3-row">
                                    <textarea name="comment" id="comment" style="width: 100%"></textarea>
                                </div>
                                <div class="w3-row w3-margin-top w3-right">
                                    <button type="button" onclick="submitComment()">
                                        <fmt:message key="Send" bundle="${rofle}"/>
                                    </button>
                                </div>
                            </form>
                        </div>
                        <div id="comments">

                        </div>
                    </div>
                    <!-- End Left Column -->
                </div>
                <!-- Right Column -->
                <div class="w3-half w3-container">
                    <!-- History -->
                    <div class=" w3-white" style="margin:0 auto;">
                        <div class="w3-row w3-padding w3-border-bottom">
                            <div class="w3-large w3-center">
                                <fmt:message key="History" bundle="${rofle}"/>
                            </div>
                        </div>
                        <div class="w3-container">
                                <table class="w3-table w3-striped">
                                    <tr>
                                        <th><fmt:message key="OrderNumber" bundle="${rofle}"/></th>
                                        <th><fmt:message key="Date" bundle="${rofle}"/></th>
                                        <th><fmt:message key="Total" bundle="${rofle}"/></th>
                                        <th><fmt:message key="DetailsLabel" bundle="${rofle}"/></th>
                                    </tr>
                                    <c:forEach items="${requestScope.get('orders')}" var="order">
                                        <tr>
                                            <td>
                                                ${order.id}
                                            </td>
                                            <td>
                                                <fmt:formatDate
                                                        value="${order.fullDate}"
                                                        type="both"/>
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${order.total}"
                                                                  type="currency" currencyCode="${order.currency}"/>
                                            </td>
                                            <td>
                                                <a class="w3-button" href="account?orderId=${order.id}">
                                                    <fmt:message key="DetailsLabel" bundle="${rofle}"/>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                        </div>
                    </div>
                    <!-- End Right Column -->
                </div>

                <!-- End Grid -->
            </div>

            <!-- End Page Container -->
        </div>
    <hr>
        <script type="text/javascript">
            <%@include file="../scripts/time.js"%>
        </script>
        <script type="text/javascript">
            <%@include file="../scripts/comments.js"%>
        </script>
        <script type="text/javascript">
            <%@include file="../scripts/ajaxLib.js"%>
        </script>
    </body>
</html>
