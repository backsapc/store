package Servlets;

import Beans.CartBean;
import Beans.InterfaceBean;
import Beans.UserBean;
import Models.InterfaceGenerator;
import Models.ResponseGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by alexthor on 13.09.17.
 */
public class CardServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            Locale locale = (Locale)request.getSession().getAttribute("locale");
            if (locale == null) {
                locale = ResponseGenerator.getLocale(getInitParameter("defaultLanguage"));
                request.getSession().setAttribute("locale", locale);
            }
            checkCart(request);
            checkUser(request);

            String product = request.getParameter("product");
            if (product == null)
                product = getInitParameter("defaultProduct");

            String tab = getDefaultTab(request);

            ResourceBundle interfaceResource = getInterfaceBundle(locale);
            ResourceBundle productResource = getResourceBundle(locale, product);
            InterfaceBean interfaceBean = InterfaceGenerator.getInterface(locale);
            StringBuilder resp = ResponseGenerator.generateResponse(interfaceResource, productResource, tab, product);
            response.setContentType("text/html; charset=UTF-8");
            request.setAttribute("page", resp.toString());
            request.setAttribute("interfaceBean", interfaceBean);
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/card.jsp");
            view.forward(request, response);
        }
        catch (MissingResourceException ex){
            response.sendError(404);
        }
    }

    private String getDefaultTab(HttpServletRequest request) {
        UserBean user = (UserBean) request.getSession().getAttribute("user");
        return user.getDefaultTab();
    }


    private void checkCart(HttpServletRequest request) {
        if(request.getSession().getAttribute("cart") == null)
            request.getSession().setAttribute("cart", new CartBean());
    }

    private void checkUser(HttpServletRequest request) {
        if(request.getSession().getAttribute("user") == null)
            request.getSession().setAttribute("user", new UserBean());
    }

    private ResourceBundle getInterfaceBundle(Locale locale) {
        return ResourceBundle.getBundle("InterfaceBundle", locale);
    }

    private ResourceBundle getResourceBundle(Locale locale, String product) {
        ResourceBundle productsResource = ResourceBundle.getBundle("ProductsBundle");
        return ResourceBundle.getBundle(productsResource.getString(product), locale);
    }
}
