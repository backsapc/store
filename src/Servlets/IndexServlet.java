package Servlets;

import Beans.CartBean;
import Beans.InterfaceBean;
import Beans.ProductPreviewBean;
import Beans.UserBean;
import Models.InterfaceGenerator;
import Models.ProductsProvider;
import Models.ResponseGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by alexthor on 14.10.17.
 */
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        checkCart(request);
        checkUser(request);

        try {
            Locale locale = (Locale)request.getSession().getAttribute("locale");
            if (locale == null) {
                locale = ResponseGenerator.getLocale(getInitParameter("defaultLanguage"));
                request.getSession().setAttribute("locale", locale);
            }
            String category = getCategory(request, response);
            String[] products = ProductsProvider.getProductsByCategory(category);
            InterfaceBean interfaceBean = InterfaceGenerator.getInterface(locale);
            String representation = getCategoryRepresentation(category, locale);
            interfaceBean.setSelectedCategory(representation);
            request.setAttribute("products", products);
            request.setAttribute("interfaceBean", interfaceBean);
        }
        catch (MissingResourceException ex){
            response.sendError(404);
        }
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/products.jsp");
        view.forward(request, response);
    }

    private void checkCart(HttpServletRequest request) {
        if(request.getSession().getAttribute("cart") == null)
            request.getSession().setAttribute("cart", new CartBean());
    }

    private void checkUser(HttpServletRequest request) {
        if(request.getSession().getAttribute("user") == null)
            request.getSession().setAttribute("user", new UserBean());
    }

    private String getCategoryRepresentation(String category, Locale locale){
        ResourceBundle bundle = ResourceBundle.getBundle("InterfaceBundle", locale);
        return bundle.getString(category);
    }

    private String getCategory(HttpServletRequest req, HttpServletResponse res){
        String category = req.getParameter("category");
        if(category != null){
            category = ProductsProvider.checkCategory(category);
            Cookie cookie = new Cookie("defaultCategory", category);
            res.addCookie(cookie);
            return category;
        }
        Cookie[] cookies = req.getCookies();
        if(cookies == null || findCookie("defaultCategory", cookies) == null)
            return getInitParameter("defaultCategory");
        String result = findCookie("defaultCategory", cookies).getValue();
        if(result == null)
            return getInitParameter("defaultCategory");
        return result;
    }

    private Cookie findCookie(String name, Cookie[] cookies){
        for (Cookie cookie :
                cookies) {
            if (name.equals(cookie.getName()))
                return cookie;
        }
        return null;
    }

}
