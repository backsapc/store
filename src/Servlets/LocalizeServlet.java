package Servlets;

import Beans.CartBean;
import Beans.ProductPreviewBean;
import Beans.UserBean;
import Models.ProductsProvider;
import Models.ResponseGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by alexthor on 13.09.17.
 */
public class LocalizeServlet extends javax.servlet.http.HttpServlet {
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
        String lang = request.getParameter("lang");
        setLocale(request, response, lang);
        updateCart(request, response);
        response.setContentType("text/html; charset=UTF-8");
        redirect(request, response, referer);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String destinationUrl)
            throws javax.servlet.ServletException, IOException {
        response.sendRedirect(destinationUrl);
    }

    private void updateCart(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        CartBean cart = (CartBean) request.getSession().getAttribute("cart");
        if(cart == null)
            return;
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        ResourceBundle productsBundle = ResourceBundle.getBundle("ProductsBundle");
        for (String product: cart.getItemsById().keySet()) {
            ProductPreviewBean productBean = ProductsProvider.getProductBean(product, locale, productsBundle);
            double price = Double.parseDouble(productBean.getPrice());
            cart.getItemsById().get(product).setPrice(price);
        }
    }

    private void setLocale(HttpServletRequest request, HttpServletResponse response, String lang){
        if (lang == null)
            lang = getInitParameter("defaultLanguage");
        Locale locale = ResponseGenerator.getLocale(lang);
        request.getSession().setAttribute("locale", locale);
    }
}
