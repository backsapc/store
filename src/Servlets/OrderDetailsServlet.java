package Servlets;

import Beans.CartBean;
import Beans.CartItemBean;
import Beans.InterfaceBean;
import Beans.UserBean;
import Entities.CheckoutDetailsEntity;
import Entities.CheckoutEntity;
import Entities.UserEntity;
import HibernatePackage.DataAccess;
import Models.DateTimeProvider;
import Models.InterfaceGenerator;
import Models.ResponseGenerator;
import Models.SomeDataBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Locale;

/**
 * Created by alexthor on 13.09.17.
 */
public class OrderDetailsServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        setInterface(request);
        UserBean user = (UserBean) request.getSession().getAttribute("user");
        if(user.getRole().equals("NotAuthorized") && request.isUserInRole("customer")) {
            user.setRole("customer");
            user.setName(request.getUserPrincipal().getName());
        }
        if(user.getRole().equals("NotAuthorized")) {
            redirect(request, response, "http://localhost:8080/");
            return;
        }
        processCheckout(request, response);
    }

    private void processCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String address = request.getParameter("address");
        if(address == null) {
            skipProceed(request, response);
            return;
        }
        UserBean user = (UserBean)request.getSession().getAttribute("user");
        String addressValue;
        if(address.equals("courier"))
            addressValue = request.getParameter("addressValue");
        else
            addressValue = SomeDataBase.getAddressByShop(address);
        checkout(request, user, address, addressValue);
        clearCart(request, response);
        redirect(request, response, "http://localhost:8080/");
    }

    private void checkout(HttpServletRequest request, UserBean user, String address, String addressValue) {
        address = address.toLowerCase();
        CheckoutEntity checkoutEntity = createCheckoutEntity(user, address, addressValue);
        DataAccess.saveCheckout(checkoutEntity);
        for (CartItemBean cartItem: getCartBean(request).getItemsById().values()) {
            CheckoutDetailsEntity detailsEntity = createCheckoutDetailsEntity(cartItem);
            detailsEntity.setOrderId(checkoutEntity.getId());
            DataAccess.saveCheckoutDetails(detailsEntity);
        }
    }

    private CheckoutEntity createCheckoutEntity(UserBean user,String address, String addressValue) {
        Date sqlDate = DateTimeProvider.getSqlDate();
        Time sqlTime = DateTimeProvider.getSqlTime();
        CheckoutEntity entity = new CheckoutEntity();
        entity.setAddress(addressValue);
        entity.setDeliveredby(address);
        entity.setDate(sqlDate);
        entity.setTime(sqlTime);
        entity.setUsername(user.getName());
        return entity;
    }

    private UserEntity createUserEntity(UserBean user){
        return DataAccess.getUser(user.getName());
    }

    private CheckoutDetailsEntity createCheckoutDetailsEntity(CartItemBean cartItem){
        CheckoutDetailsEntity entity = new CheckoutDetailsEntity();
        entity.setProductName(cartItem.getProduct());
        entity.setAmount(cartItem.getNumberOrdered());
        entity.setPrice(cartItem.getPrice());
        return entity;
    }

    private void skipProceed(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = "WEB-INF/checkout.jsp";
        RequestDispatcher view = request.getRequestDispatcher(uri);
        view.forward(request, response);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String destinationUrl)
            throws ServletException, IOException {
        response.sendRedirect(destinationUrl);
    }

    private void setInterface(HttpServletRequest request) {
        Locale locale = (Locale)request.getSession().getAttribute("locale");
        if (locale == null) {
            locale = ResponseGenerator.getLocale(getInitParameter("defaultLanguage"));
            request.getSession().setAttribute("locale", locale);
        }
        InterfaceBean interfaceBean = InterfaceGenerator.getInterface(locale);
        request.setAttribute("interfaceBean", interfaceBean);
    }

    private void clearCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CartBean cart = getCartBean(request);
        if(cart == null)
            return;
        String[] products = cart.getItemsById().keySet().toArray(new String[0]);
        for (String product: products) {
            cart.throwItemById(product);
        }


    }

    private CartBean getCartBean(HttpServletRequest request) {
        return (CartBean) request.getSession().getAttribute("cart");
    }

    private void checkCart(HttpServletRequest request) {
        if(request.getSession().getAttribute("cart") == null)
            request.getSession().setAttribute("cart", new CartBean());
    }

    private void checkUser(HttpServletRequest request) {
        if(request.getSession().getAttribute("user") == null)
            request.getSession().setAttribute("user", new UserBean());
    }

}
