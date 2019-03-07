package Servlets;//package Servlets;
import Beans.UserBean;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;


/**
 * Created by alexthor on 13.09.17.
 */
public class LogFilter implements Filter {
    private ServletContext context;
    private final static Logger logger = Logger.getLogger(LogFilter.class);
    public LogFilter() {}
    public void destroy() {}
    public void doFilter(ServletRequest request, ServletResponse
            response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        String ip = httpReq.getRemoteAddr();
        String uri = httpReq.getRequestURI();
        UserBean user = (UserBean) httpReq.getSession().getAttribute("user");
        boolean userExist = user != null;
        String username = userExist ? user.getName() : "anonymous";
        StringBuilder logMessage = createLogMessage(httpReq, uri, username);
        logger.info(logMessage.toString());
        //context.log(logMessage.toString());
        chain.doFilter(request, response);
    }

    private StringBuilder createLogMessage(HttpServletRequest httpReq, String uri, String username) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("User: ").append(username)
                .append(" made request to ").append(uri)
                .append(" with request params ");
        Enumeration<String> requestKeys = httpReq.getParameterNames();
        List<String> requestKeysList = Collections.list(requestKeys);
        for (String requestKey : requestKeysList) {
            String param = httpReq.getParameter(requestKey);
            logMessage.append(param);
        }
        return logMessage;
    }

    public void init(FilterConfig config) throws ServletException
    {
        context = config.getServletContext();
    }
}
