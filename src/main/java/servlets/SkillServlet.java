package servlets;

import models.Exception.UserNotFound;
import models.JobOonJa.JobOonJa;
import models.Skill.Skill;
import models.Skill.UserSkill;
import models.User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/skill")
public class SkillServlet extends HttpServlet {
    private JobOonJa jobOonJa = JobOonJa.getInstance();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        String loggedInUser = jobOonJa.getLogInUser();
        String userId = req.getParameter("userId");
        String skillName = req.getParameter("skillName");
        try {
            jobOonJa.deleteUserSkill(userId,skillName);
            User user = jobOonJa.getUser(userId);
            req.setAttribute("user",user);
            ArrayList<Skill> abilities = jobOonJa.getUserAbilities(loggedInUser);
            req.setAttribute("abilities", abilities);
        } catch (UserNotFound userNotFound) {
            req.setAttribute("message", userNotFound.getMessage());
            req.getRequestDispatcher("/exception.jsp").forward(req, resp);
            userNotFound.printStackTrace();
        }
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        String loggedInUser = jobOonJa.getLogInUser();
        String userId = req.getParameter("userId");
        String skillName = req.getParameter("skill");
        try {
            jobOonJa.addSkillToUser(userId, skillName);
            ArrayList<Skill> abilities = jobOonJa.getUserAbilities(loggedInUser);
            User user = jobOonJa.getUser(userId);
            req.setAttribute("user",user);
            req.setAttribute("abilities", abilities);
        } catch (UserNotFound userNotFound) {
            req.setAttribute("message", userNotFound.getMessage());
            req.getRequestDispatcher("/exception.jsp").forward(req, resp);
            userNotFound.printStackTrace();
        }
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}
