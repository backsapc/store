<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<div class="w3-container">
    <!--hr/-->
     <div class="w3-bar w3-container w3-border-bottom w3-border-top w3-margin-top">
         <a href="products" class="w3-bar-item w3-left w3-button">
             Surface store
         </a>
         <c:choose>
             <c:when test="${user.role eq 'NotAuthorized'}">
                 <a href="auth" class="w3-bar-item w3-button w3-right w3-hover-blue">
                         ${interfaceBean.loginLabel}</a>
             </c:when>
             <c:otherwise>
                 <a href="auth?logout=true" class="w3-bar-item w3-button w3-right w3-hover-blue">
                         ${interfaceBean.logoutLabel}
                 </a>
                 <a href="account" class="w3-bar-item w3-button w3-right w3-hover-blue">
                         ${interfaceBean.profileLabel}
                 </a>
                 <c:if test="${cart.itemsAmount > 0}">
                     <a href="checkout" class="w3-bar-item w3-button w3-right w3-hover-blue">
                             ${interfaceBean.checkoutLabel}
                     </a>
                 </c:if>
             </c:otherwise>
         </c:choose>
         <a href="cart" class="w3-bar-item w3-button w3-right w3-hover-blue">
             <c:choose>
                 <c:when test="${cart.itemsAmount > 0}">
                     ${interfaceBean.itemsLabel} : ${cart.itemsAmount}
                 </c:when>
                 <c:otherwise>
                     ${interfaceBean.cartLabel}
                 </c:otherwise>
             </c:choose>
         </a>
         <div class="w3-bar-item w3-dropdown-hover w3-right w3-cell-middle">
             ${interfaceBean.langLabel}
             <div class="w3-dropdown-content w3-bar-block w3-border">
                 <a href="localize?lang=ru" class="w3-bar-item w3-button">Русский</a>
                 <a href="localize?lang=en" class="w3-bar-item w3-button">English</a>
                 <a href="localize?lang=nl" class="w3-bar-item w3-button">Nederlands</a>
             </div>
         </div>
     </div>
     <!--hr-->
</div>