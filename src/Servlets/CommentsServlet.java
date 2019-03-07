package Servlets;//package Servlets;

import Beans.CartBean;
import Beans.InterfaceBean;
import Beans.UserBean;
import Entities.CheckoutDetailsEntity;
import Entities.CheckoutEntity;
import Entities.CommentEntity;
import Entities.UserEntity;
import HibernatePackage.DataAccess;
import Models.DateTimeProvider;
import Models.InterfaceGenerator;
import Models.ResponseGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

//import DataAccess;

/**
 * Created by alexthor on 13.09.17.
 */
public class CommentsServlet extends javax.servlet.http.HttpServlet {
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
        UserEntity userEntity = setUpUser(request, user);
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        if(user.getRole().equals("NotAuthorized")) {
            redirect(request, response, "http://localhost:8080/");
            return;
        }

        String action = request.getParameter("action");
        if(action != null) {
            switch (action){
                case "add":
                    processAddComment(request, user);
                    break;
                case "delete":
                case "get":
                default:
                    StringBuilder commentsResponse = processGetComments(user, locale);
                    writeResponse(response, commentsResponse.toString());
                    break;
            }
        }
    }

    private StringBuilder processGetComments(UserBean user, Locale locale) {
        List<CommentEntity> commentEntities = DataAccess.getComments(user.getName());
        if(commentEntities == null)
            commentEntities = new LinkedList<>();
        Collections.reverse(commentEntities);
        StringBuilder commentsResponse = new StringBuilder();
        if(commentEntities.isEmpty())
            sendNoComments(commentsResponse, locale);
        else {
            sendFoundComments(locale, commentEntities, commentsResponse);
        }
        return commentsResponse;
    }

    private void sendFoundComments(Locale locale, List<CommentEntity> commentEntities, StringBuilder commentsResponse) {
        commentsResponse.append("<ul class=\"w3-ul w3-border-top w3-border-bottom\">");
        for (CommentEntity commentEnt: commentEntities) {
            commentsResponse.append("<li>")
                            .append("<div class=\"w3-container w3-light-grey w3-padding\">\n")
                            .append("  <div class='w3-row w3-border-bottom w3-padding'>")
                            .append(commentEnt.getText())
                            .append("</div>\n")
                            .append("  <div class='w3-row w3-small w3-right'>")
                            .append(DateTimeProvider.formatDate(commentEnt.getFullDate(), locale))
                            .append("</div>\n")
                            .append("</div>")
                            .append("</li>");
        }
        commentsResponse.append("</ul>");
    }

    private void sendNoComments(StringBuilder commentsResponse, Locale locale) {
        ResourceBundle interfaceBundle = ResourceBundle.getBundle("InterfaceBundle", locale);
        commentsResponse.append(
            "<ul class=\"w3-ul\">" +
                "<li class='w3-large w3-border-top w3-border-bottom'>" +
                    interfaceBundle.getString("ReviewsContent") +
                "</li>" +
            "</ul>"
        );
    }

    private void processAddComment(HttpServletRequest request, UserBean user) throws UnsupportedEncodingException {
        String comment = request.getParameter("comment");
        comment = decodeString(comment);
        CommentEntity commentEntity = createCommentEntity(user.getName(), comment);
        DataAccess.saveComment(commentEntity);
    }

    private String decodeString(String input) throws UnsupportedEncodingException {
        String rightString= new String(input.getBytes("ISO-8859-1"),"utf-8");
        return rightString;
    }


    private void writeResponse(HttpServletResponse response,
                               String commentsResponse) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(commentsResponse);
        out.close();
    }

    private CommentEntity createCommentEntity(UserEntity entity, String comment){
        return createCommentEntity(entity.getUsername(), comment);
    }

    private CommentEntity createCommentEntity(String username, String comment){
        CommentEntity entity = new CommentEntity();
        entity.setUsername(username);
        entity.setText(comment);
        entity.setDate(DateTimeProvider.getSqlDate());
        entity.setTime(DateTimeProvider.getSqlTime());
        return entity;
    }


    private void performAction(String action, HttpServletRequest request, HttpServletResponse response) {

    }

    private UserEntity setUpUser(HttpServletRequest request, UserBean user) {
        UserEntity entity = null;
        if(user.getRole().equals("NotAuthorized") && request.isUserInRole("customer")) {
            user.setRole("customer");
            user.setName(request.getUserPrincipal().getName());
            entity = DataAccess.getUser(user.getName());
            user.setDefaultTab(entity.getDefaulttab());
        }
        return entity;
    }

    private void setUpRequest(HttpServletRequest request) {
        checkCart(request);
        checkUser(request);
        setInterface(request);
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
