package Servlets;

import Beans.CartBean;
import Beans.CartItemBean;
import Beans.ProductPreviewBean;
import Beans.UserBean;
import Models.ProductsProvider;
import Models.ResponseGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by alexthor on 13.09.17.
 */
public class CartProcessorServlet extends javax.servlet.http.HttpServlet {
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
        request.setCharacterEncoding("UTF-8");
        String referer = request.getHeader("referer");
        if(referer == null) {
            redirect(request, response, request.getHeader("host"));
            return;
        }

        String action = request.getParameter("action");
        if(action == null) {
            redirect(request, response, referer);
            return;
        }

        String product = request.getParameter("product");
        if(product == null) {
            redirect(request, response, referer);
            return;
        }

        ResourceBundle productsResource = ResourceBundle.getBundle("ProductsBundle");
        if(!productsResource.containsKey(product)){
            redirect(request, response, referer);
            return;
        }

        checkCart(request);
        checkUser(request);

        switch (action){
            case "add":
            case "remove":
            case "throw":
                processAction(request, response, action, product);
                break;
            default:
                redirect(request, response, request.getHeader("host"));
                return;
        }

        response.setContentType("text/html; charset=UTF-8");
        redirect(request, response, referer);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String destinationUrl)
            throws javax.servlet.ServletException, IOException {
        response.sendRedirect(destinationUrl);
    }

    private void processAction(HttpServletRequest request, HttpServletResponse response, String action ,String product){
        if(action.equals("add"))
            processAdd(request, response, product);
        if(action.equals("remove"))
            processRemove(request, response, product);
        if(action.equals("throw"))
            processThrow(request, response, product);
    }

    private void processAdd(HttpServletRequest request, HttpServletResponse response, String product){
        CartBean cart = (CartBean)request.getSession().getAttribute("cart");
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        ResourceBundle productsBundle = ResourceBundle.getBundle("ProductsBundle");
        ProductPreviewBean productBean = ProductsProvider.getProductBean(product, locale, productsBundle);
        double price = Double.parseDouble(productBean.getPrice());
        cart.addItem(new CartItemBean(product, 1, price));
        request.getSession().setAttribute("cart", cart);
        //throw new NotImplementedException();
    }

    private void processRemove(HttpServletRequest request, HttpServletResponse response, String product){
        CartBean cart = (CartBean)request.getSession().getAttribute("cart");
        cart.removeItemById(product);
        request.getSession().setAttribute("cart", cart);
    }

    private void processThrow(HttpServletRequest request, HttpServletResponse response, String product){
        CartBean cart = (CartBean)request.getSession().getAttribute("cart");
        cart.throwItemById(product);
        request.getSession().setAttribute("cart", cart);
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
