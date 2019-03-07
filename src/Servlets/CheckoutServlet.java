package Servlets;

import Beans.CartBean;
import Beans.CartItemBean;
import Beans.InterfaceBean;
import Beans.UserBean;
import Entities.CheckoutDetailsEntity;
import Entities.CheckoutEntity;
import Entities.UserEntity;
import HibernatePackage.DataAccess;
import Models.*;
import com.sun.deploy.net.HttpRequest;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Time;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Created by alexthor on 13.09.17.
 */
public class CheckoutServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        checkCart(request);
        checkUser(request);
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
            addressValue = SomeDataBase.getAddressByShop(address.toLowerCase());
        addressValue = decodeString(addressValue);
        checkout(request, user, address, addressValue);
        clearCart(request, response);
        redirect(request, response, "http://localhost:8080/");
    }


    private String decodeString(String input) throws UnsupportedEncodingException {
        String rightString= new String(input.getBytes("ISO-8859-1"),"utf-8");
        return rightString;
    }


    private void checkout(HttpServletRequest request, UserBean user, String address, String addressValue) {
        String currency = getCurrency(request);
        CartBean cart = getCartBean(request);
        CheckoutEntity checkoutEntity = createCheckoutEntity(user, address, addressValue, cart, currency);
        DataAccess.saveCheckout(checkoutEntity);
        for (CartItemBean cartItem: cart.getItemsById().values()) {
            CheckoutDetailsEntity detailsEntity = createCheckoutDetailsEntity(cartItem);
            detailsEntity.setOrderId(checkoutEntity.getId());
            DataAccess.saveCheckoutDetails(detailsEntity);
        }
    }

    private CheckoutEntity createCheckoutEntity(UserBean user, String address,
                                                String addressValue, CartBean cart,
                                                String currency) {
        Date sqlDate = DateTimeProvider.getSqlDate();
        Time sqlTime = DateTimeProvider.getSqlTime();
        CheckoutEntity entity = new CheckoutEntity();
        entity.setAddress(addressValue);
        entity.setDeliveredby(address);
        entity.setDate(sqlDate);
        entity.setTime(sqlTime);
        entity.setUsername(user.getName());
        entity.setCurrency(currency);
        entity.setTotal(cart.getTotalPrice());
        return entity;
    }


    private String getCurrency(HttpServletRequest request){
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        String result = formatter.getCurrency().getCurrencyCode();
        return result;
    }


    private UserEntity createUserEntity(UserBean user){
        return DataAccess.getUser(user.getName());
    }

    private CheckoutDetailsEntity createCheckoutDetailsEntity(CartItemBean cartItem){
        CheckoutDetailsEntity entity = new CheckoutDetailsEntity();
        entity.setProductName(getProductName(cartItem.getProduct()));
        entity.setAmount(cartItem.getNumberOrdered());
        entity.setPrice(cartItem.getPrice());
        return entity;
    }


    private String getProductName(String productId){
        return ProductsProvider.getProductName(productId);
    }


    private void skipProceed(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = "WEB-INF/checkout.jsp";
        RequestDispatcher view = request.getRequestDispatcher(uri);
        view.forward(request, response);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String destinationUrl)
            throws javax.servlet.ServletException, IOException {
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
            throws javax.servlet.ServletException, IOException {
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
