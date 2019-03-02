package servlets;

import models.Exception.UserNotFound;
import models.JobOonJa.JobOonJa;
import models.Project.Project;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

@WebServlet({"/project", "/project/*"})
public class ProjectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        StringTokenizer stringTokenizer = new StringTokenizer(url, "/");
        ArrayList<Project> projects = null;
        try {
            projects = JobOonJa.getInstance().getSuitableProjects("1");
        } catch (UserNotFound userNotFound) {
            userNotFound.printStackTrace();
        }
        if (stringTokenizer.countTokens() == 2) {
            req.setCharacterEncoding("UTF-8");
            req.setAttribute("projects", projects);
            req.getRequestDispatcher("projects.jsp").forward(req, resp);
        } else if (stringTokenizer.countTokens() == 3) {

        }
    }
}
