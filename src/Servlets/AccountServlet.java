package Servlets;//package Servlets;

import Beans.CartBean;
import Beans.InterfaceBean;
import Beans.UserBean;
//import DataAccess;
import Entities.CheckoutDetailsEntity;
import Entities.CheckoutEntity;
import Entities.UserEntity;
import HibernatePackage.DataAccess;
import Models.InterfaceGenerator;
import Models.ResponseGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by alexthor on 13.09.17.
 */
public class AccountServlet extends javax.servlet.http.HttpServlet {
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
        setUpRequest(request);
        UserBean user = (UserBean) request.getSession().getAttribute("user");
        setUpUser(request, user);

        if(user.getRole().equals("NotAuthorized")) {
            redirect(request, response, "http://localhost:8080/");
            return;
        }

        String orderId = request.getParameter("orderId");
        CheckoutEntity order = null;

        if(orderId != null)
             order = DataAccess.getOrder(Integer.parseInt(orderId));

        if(order != null){
            checkDetailsRequested(request, response, order);
            return;
        }

        String defaultTab = request.getParameter("defaultTab");
        if(defaultTab != null){
            checkDefaultTab(user, defaultTab);
            request.getSession().setAttribute("user", user);
            redirect(request, response, "http://localhost:8080/account");
            return;
        }
        setOrders(request, user);
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/account.jsp");
        view.forward(request, response);
    }

    private void setUpUser(HttpServletRequest request, UserBean user) {
        if(user.getRole().equals("NotAuthorized") && request.isUserInRole("customer")) {
            user.setRole("customer");
            user.setName(request.getUserPrincipal().getName());
            UserEntity entity = DataAccess.getUser(user.getName());
            user.setDefaultTab(entity.getDefaulttab());
        }
    }

    private void setUpRequest(HttpServletRequest request) {
        checkCart(request);
        checkUser(request);
        setInterface(request);
    }

    private void checkDetailsRequested(HttpServletRequest request, HttpServletResponse response, CheckoutEntity order) throws ServletException, IOException {
        List<CheckoutDetailsEntity> orderDetails = DataAccess.getCheckoutDetails(order);
        request.setAttribute("order", order);
        request.setAttribute("orderDetails", orderDetails);
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/orderDetails.jsp");
        view.forward(request, response);
    }

    private void setOrders(HttpServletRequest request, UserBean user) {
        List<CheckoutEntity> orders = DataAccess.getOrders(user.getName());
        request.setAttribute("orders", orders);
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

    private void checkDefaultTab(UserBean user, String defaultTab) {
        switch (defaultTab){
            case "Overview":
            case "TechSpecs":
            case "Reviews":
                user.setDefaultTab(defaultTab);
                DataAccess.updateUser(user);
                break;
            default:
                user.setDefaultTab("Overview");
        }
    }

    private void processCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = "http://localhost:8080/";
        clearCart(request, response);
        redirect(request, response, uri);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String destinationUrl)
            throws ServletException, IOException {
        response.sendRedirect(destinationUrl);
    }

    private void clearCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CartBean cart = (CartBean) request.getSession().getAttribute("cart");
        if(cart == null)
            return;
        String[] products = cart.getItemsById().keySet().toArray(new String[0]);
        for (String product: products) {
            cart.throwItemById(product);
        }
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
