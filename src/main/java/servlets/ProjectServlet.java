package servlets;

import models.JobOonJa.JobOonJa;
import models.Project.Project;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet({"/projects", "/projects/*"})
public class ProjectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathParts = req.getPathInfo();
        String parameter = req.getQueryString();
        if (pathParts == null) {
            this.renderProjectsList(req, resp);
        } else {
            this.renderProjectData(req, resp, pathParts);
        }
    }

    private void renderProjectData(HttpServletRequest req, HttpServletResponse resp, String pathParts) {
        try {
            Project project = JobOonJa.getInstance().getSuitableProject("1", pathParts.substring(1));
            boolean hasBid = JobOonJa.getInstance().findBid("1", project.getTitle());
            req.setCharacterEncoding("UTF-8");
            req.setAttribute("project", project);
            req.setAttribute("hasBid", hasBid);
            req.getRequestDispatcher("/project.jsp").forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void renderProjectsList(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ArrayList<Project> projects = JobOonJa.getInstance().getSuitableProjects("1");
            req.setCharacterEncoding("UTF-8");
            req.setAttribute("projects", projects);
            req.getRequestDispatcher("/projects.jsp").forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
