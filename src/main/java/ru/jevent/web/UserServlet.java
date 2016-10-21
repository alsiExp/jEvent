package ru.jevent.web;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.jevent.LoggerWrapper;
import ru.jevent.model.User;
import ru.jevent.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private static final LoggerWrapper LOG = LoggerWrapper.get(UserServlet.class);
    private WebApplicationContext wac;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to userList");
        UserService userService = wac.getBean(UserService.class);
        List<User> userList = userService.getAll();
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/jsp/userList.jsp").forward(request, response);
//        response.sendRedirect("userList.jsp");
    }
}
