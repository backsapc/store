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
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="InterfaceBundle" var="rofle"/>
    <title>Surface store</title>
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript">
    </script>
</head>
<body>
<%@include file="header.jsp"%>
        <div class="w3-container w3-content" style="max-width:1400px;margin-top:15px">
            <!-- The Grid -->
            <div class="w3-row">
                <!-- Left Column -->
                <div class="w3-half w3-padding">
                    <!-- Profile -->
                    <div class="w3-card w3-white " style="margin:0 auto;">
                        <div class="w3-white " style="margin:0 auto;">
                            <div class="w3-container">
                                <div>
                                    <p class="w3-xlarge"><fmt:message key="CheckoutDetails" bundle="${rofle}"/></p>
                                </div>
                            </div>
                        </div>
                        <div class="w3-container">
                            <hr>
                            <div>
                                <p class="w3-large">
                                    <fmt:message key="Summary" bundle="${rofle}"/>
                                </p>
                            </div>
                            <hr>
                            <div class="w3-row w3-margin-bottom">
                                <div class="w3-container w3-padding">
                                    <table class="w3-table w3-striped w3-padding">
                                        <tr>
                                            <td><fmt:message key="Items" bundle="${rofle}"/></td>
                                            <td>${cart.itemsAmount}</td>
                                        </tr>
                                        <tr>
                                            <td><fmt:message key="Total" bundle="${rofle}"/></td>
                                            <td><fmt:formatNumber value="${cart.totalPrice}"
                                                                  type="currency"/></td>
                                        </tr>
                                    </table>
                                    <hr>
                                    <div>
                                        <p class="w3-large">
                                            <fmt:message key="ProductsListLabel" bundle="${rofle}"/>
                                        </p>
                                    </div>
                                    <table class="w3-table w3-striped w3-padding">
                                        <tr>
                                            <th><fmt:message key="Product" bundle="${rofle}"/></th>
                                            <th><fmt:message key="Items" bundle="${rofle}"/></th>
                                            <th><fmt:message key="Price" bundle="${rofle}"/></th>
                                        </tr>
                                        <c:forEach items="${cart.itemsById}" var="order">
                                            <tr>
                                                <td>
                                                        ${order.value.product}
                                                </td>
                                                <td>
                                                        ${order.value.numberOrdered}
                                                </td>
                                                <td>
                                                    <fmt:formatNumber value="${order.value.price}"
                                                                      type="currency"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                                <hr>
                            </div>
                        </div>

                    </div>
                    <!-- End Left Column -->
                </div>

                <!-- Right Column -->
                <div class="w3-half w3-padding">
                    <div class="w3-card w3-white " style="margin:0 auto;">
                        <div class="w3-white " style="margin:0 auto;">
                            <div class="w3-container">
                                <div>
                                    <p class="w3-xlarge"><fmt:message key="DeliveryDetails" bundle="${rofle}"/></p>
                                </div>
                                <hr>
                                <form class="w3-container" method="post" accept-charset="UTF-8">
                                    <input class="w3-radio" type="radio"
                                           name="address" value="Saint-P Shop Lig" checked>
                                    <label>Saint-P Shop Lig</label>
                                    <p>
                                        <input class="w3-radio" type="radio"
                                               name="address" value="Saint-P Shop Col">
                                        <label>Saint-P Shop Col</label>
                                    </p>
                                    <p>
                                        <input class="w3-radio" type="radio"
                                               name="address" value="Saint-P Shop Yak">
                                        <label>Saint-P Shop Yak</label>
                                    </p>
                                    <p>
                                        <input class="w3-radio" type="radio"
                                               name="address" value="courier">
                                        <label><fmt:message key="Courier" bundle="${rofle}"/></label>
                                    </p>
                                    <p>
                                        <input class="w3-input w3-border" type="text"
                                               name="addressValue" value="" disabled>
                                    </p>
                                    <div class="w3-row w3-padding">
                                        <input type="submit" name="submitButton"
                                                class="w3-button w3-blue w3-right"
                                               value='<fmt:message key="Checkout" bundle="${rofle}"/>'/>
                                    </div>
                                </form>
                                <hr>
                                    <div>
                                        <div id="map" style="width: 600px; height: 400px"/>
                                    </div>
                                <hr>
                            </div>
                        </div>
                        <hr>
                    </div>
                </div>
                <!-- End Right Column -->
            </div>


            <!-- End Grid -->
        </div>

        <!-- End Page Container -->
        <hr>
        <script>
            ymaps.ready(init);
            var myMap,
                myPlacemark,
                shop1,
                shop2,
                shop3;
            var buttons = document.getElementsByName("address");
            var customAddressLabel = document.getElementsByName("addressValue");
            var map = document.getElementById("map");
            var associativeArray;


            function setColor(event) {
                if(event.target.value === "courier"){
                    customAddressLabel[0].disabled = 0;
                    map.style.display = 'none';
                    return;
                }
                customAddressLabel[0].disabled = 1;
                map.style.display = 'block';
                onChange(event.target.value);
            }

            // Set on change listeners
            for (var i = 0; i < buttons.length; i++)
                buttons[i].addEventListener("change", setColor);

            // Y.Map init
            function init() {
                myMap = new ymaps.Map("map", {
                    center: [59.963181, 30.255643],
                    zoom: 9
                });

                myPlacemark = new ymaps.Placemark([55.76, 37.64], {
                    hintContent: 'Москва!',
                    balloonContent: 'Столица России'
                });

                shop1 = new ymaps.Placemark([59.922034, 30.355502], {
                    hintContent: 'Saint-P Shop Lig!',
                    balloonContent: 'Превосходный магазин в самом центре Петербурга.'
                });

                shop1.events.add('click', function (e) {
                    buttons[0].checked = 1;
                });

                shop2 = new ymaps.Placemark([60.005510, 30.300143], {
                    hintContent: 'Saint-P Shop Col!',
                    balloonContent: 'Удобный магазин на севере Петербурга.'
                });

                shop2.events.add('click', function (e) {
                    buttons[1].checked = 1;
                });

                shop3 = new ymaps.Placemark([59.981347, 30.209349], {
                    hintContent: 'Saint-P Shop Yak!',
                    balloonContent: 'Восхитительный магазин на берегу Финского залива.'
                });

                shop3.events.add('click', function (e) {
                    buttons[2].checked = 1;
                });

                myMap.geoObjects.add(myPlacemark);
                myMap.geoObjects.add(shop1);
                myMap.geoObjects.add(shop2);
                myMap.geoObjects.add(shop3);
                associativeArray = {
                    "Saint-P Shop Lig": shop1,
                    "Saint-P Shop Col": shop2,
                    "Saint-P Shop Yak": shop3
                };
            }

            function onChange(param) {
                var shopPoint = associativeArray[param];
                shopPoint.balloon.open()
                var coordinates = shopPoint.geometry.getCoordinates();
                myMap.setCenter(coordinates);
            }
        </script>

    </body>
</html>