package servlets;

import com.jsoniter.output.JsonStream;
import models.JobOonJa.JobOonJa;
import models.Project.Project;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class ProjectService {
    @RequestMapping(value = "/JobOonJa_war/project", method = RequestMethod.GET)
    public Project getProjects(){
        System.out.println("projects");
        try {
            return JobOonJa.getInstance().getSuitableProjects(JobOonJa.getInstance().getLogInUser()).get(0);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String pathParts = req.getPathInfo();
//        if (pathParts == null) {
//            this.sendProjectsList(req, resp);
//        } else {
//            this.renderProjectData(req, resp, pathParts);
//        }
//    }
//
//    private void renderProjectData(HttpServletRequest req, HttpServletResponse resp, String pathParts) throws ServletException, IOException {
//        try {
//            Project project = JobOonJa.getInstance().getSuitableProject(JobOonJa.getInstance().getLogInUser(), pathParts.substring(1));
//            boolean hasBid = JobOonJa.getInstance().findBid(JobOonJa.getInstance().getLogInUser(), project.getId());
//            req.setCharacterEncoding("UTF-8");
//            req.setAttribute("project", project);
//            req.setAttribute("hasBid", hasBid);
//            req.getRequestDispatcher("/project.jsp").forward(req, resp);
//        } catch (Exception exception) {
//            req.setAttribute("message", exception.getMessage());
//            req.getRequestDispatcher("/exception.jsp").forward(req, resp);
//            exception.printStackTrace();
//        }
//    }

//    private void sendProjectsList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            ArrayList<Project> projects = JobOonJa.getInstance().getSuitableProjects(JobOonJa.getInstance().getLogInUser());
//            resp.getWriter().write(JsonStream.serialize(projects));
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }
}
