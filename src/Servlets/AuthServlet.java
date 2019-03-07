package Servlets;//package Servlets;

import Beans.CartBean;
import Beans.UserBean;
import Entities.UserEntity;
import HibernatePackage.DataAccess;
//import DataAccess;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexthor on 13.09.17.
 */
public class AuthServlet extends javax.servlet.http.HttpServlet {
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
        checkCart(request);
        checkUser(request);

        String referer = request.getHeader("referer");
        if(referer == null) {
            redirect(request, response, request.getHeader("host"));
            return;
        }

        UserBean user = (UserBean) request.getSession().getAttribute("user");
        if(user.getRole().equals("NotAuthorized") && request.isUserInRole("customer")) {
            String username = request.getUserPrincipal().getName();
            UserEntity entity = DataAccess.getUser(username);
            if(entity == null) {
                user.setName(username);
                entity = SaveUserEntity(user);
            }
            user.setRole("customer");
            user.setName(entity.getUsername());
            user.setDefaultTab(entity.getDefaulttab());
        }
        String logoutRequested = request.getParameter("logout");
        if(user.getRole().equals("customer") && logoutRequested != null && logoutRequested.equals("true")) {
            request.getSession().invalidate();
            redirect(request, response, "http://localhost:8080/");
            return;
        }
        redirect(request, response, referer);
    }

    private UserEntity SaveUserEntity(UserBean user) {
        UserEntity entity;
        entity = new UserEntity();
        entity.setUsername(user.getName());
        entity.setDefaulttab(user.getDefaultTab());
        DataAccess.saveUser(entity);
        return entity;
    }

    private void checkLogoutRequested(HttpServletRequest request) {

    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String destinationUrl)
            throws ServletException, IOException {
        response.sendRedirect(destinationUrl);
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
