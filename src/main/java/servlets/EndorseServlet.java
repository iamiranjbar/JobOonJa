package servlets;

import models.Exception.UserNotFound;
import models.JobOonJa.JobOonJa;
import models.User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/endorse")
public class EndorseServlet extends HttpServlet {
    private JobOonJa jobOonJa = JobOonJa.getInstance();

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        String loggedInUser = jobOonJa.getLogInUser();
        String skillName = req.getParameter("skillName");
        String endorsed = req.getParameter("userId");
        try {
            jobOonJa.endorse(loggedInUser, endorsed, skillName);
            User user = jobOonJa.getUser(endorsed);
            req.setAttribute("user",user);
        } catch (UserNotFound userNotFound) {
            userNotFound.printStackTrace();
        }
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }
}
