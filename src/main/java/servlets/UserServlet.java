package servlets;

import models.Exception.UserNotFound;
import models.JobOonJa.JobOonJa;
import models.Skill.Skill;
import models.User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet({"/users","/users/*"})
public class UserServlet extends HttpServlet {
    private JobOonJa jobOonJa = JobOonJa.getInstance();
    private String loggedInUser = "1";
    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            ArrayList<User> users = jobOonJa.getUserList(loggedInUser);
            req.setAttribute("users",users);
            req.getRequestDispatcher("/users.jsp").forward(req, resp);
        } else {
            String[] pathParts = pathInfo.split("/");
            String userId = pathParts[1];
            User user = null;
            try {
                user = jobOonJa.getUser(userId);
            } catch (UserNotFound userNotFound) {
                userNotFound.printStackTrace();
            }
            req.setAttribute("user", user);
            if (userId.equals(loggedInUser)) {
                try {
                    ArrayList<Skill> abilities = jobOonJa.getUserAbilities(loggedInUser);
                    req.setAttribute("abilities", abilities);
                } catch (UserNotFound userNotFound) {
                    userNotFound.printStackTrace();
                }
                req.getRequestDispatcher("/home.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/profile.jsp").forward(req, resp);
            }
        }
    }
}