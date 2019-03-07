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
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by alexthor on 13.09.17.
 */
public class CartServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            ResourceBundle interfaceResource = getInterfaceBundle(locale);
            InterfaceBean interfaceBean = InterfaceGenerator.getInterface(locale);
            response.setContentType("text/html; charset=UTF-8");
            request.setAttribute("interfaceBean", interfaceBean);
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/cart.jsp");
            view.forward(request, response);
        }
        catch (MissingResourceException ex){
            response.sendError(404);
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

    private ResourceBundle getInterfaceBundle(Locale locale) {
        return ResourceBundle.getBundle("InterfaceBundle", locale);
    }
}
