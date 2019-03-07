
package Servlets;

import Beans.CartBean;
import Beans.InterfaceBean;
import Beans.ProductPreviewBean;
import Beans.UserBean;
import Models.InterfaceGenerator;
import Models.ProductsProvider;
import Models.ResponseGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by alexthor on 15.10.17.
 */
public class ProductPreviewServlet extends HttpServlet {

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

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Locale locale = (Locale)request.getSession().getAttribute("locale");
        if (locale == null) {
            locale = ResponseGenerator.getLocale(getInitParameter("defaultLanguage"));
            request.getSession().setAttribute("locale", locale);
        }
        checkCart(request);
        checkUser(request);

        String product = request.getParameter("product");
        if(product == null)
            product = "SurfacePro";
        String page = request.getParameter("page");
        if(page == null)
            page = "productPreview.jsp";
        String isCart = request.getParameter("isCart");
        if (isCart != null && isCart.equals("1"))
            setCartItem(request, product);
        ResourceBundle productsBundle = ResourceBundle.getBundle("ProductsBundle");
        ProductPreviewBean productBean = ProductsProvider.getProductBean(product, locale, productsBundle);
        InterfaceBean interfaceBean = InterfaceGenerator.getInterface(locale);
        request.setAttribute("productBean", productBean);
        request.setAttribute("interfaceBean", interfaceBean);
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/" + page);
        view.include(request, response);
    }

    private void setCartItem(HttpServletRequest request, String product) {
        CartBean cart = (CartBean) request.getSession().getAttribute("cart");
        request.setAttribute("cartItemBean",cart.getItemById(product));
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
