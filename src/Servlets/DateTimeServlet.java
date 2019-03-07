package Servlets;//package Servlets;

import Beans.CartBean;
import Beans.InterfaceBean;
import Beans.UserBean;
import Entities.CommentEntity;
import Entities.UserEntity;
import HibernatePackage.DataAccess;
import Models.DateTimeProvider;
import Models.InterfaceGenerator;
import Models.ResponseGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

//import DataAccess;

/**
 * Created by alexthor on 13.09.17.
 */
public class DateTimeServlet extends javax.servlet.http.HttpServlet {
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
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        if(locale == null)
            locale = Locale.US;
        Date date = DateTimeProvider.getCurrentDate();
        String dateTime = DateTimeProvider.formatDate(date ,locale);
        writeResponse(response, dateTime);
    }

    private void writeResponse(HttpServletResponse response,
                               String commentsResponse) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(commentsResponse);
        out.close();
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String destinationUrl)
            throws ServletException, IOException {
        response.sendRedirect(destinationUrl);
    }
}
