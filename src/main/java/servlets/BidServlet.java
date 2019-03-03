package servlets;

import models.Exception.InsufficentBudget;
import models.Exception.InsufficentSkill;
import models.Exception.ProjectNotFound;
import models.Exception.UserNotFound;
import models.JobOonJa.JobOonJa;
import models.Project.Project;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bid")
public class BidServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String amount = req.getParameter("bidAmount");
        String userId = req.getParameter("userId");
        String projectId = req.getParameter("projectId");
        try {
            JobOonJa.getInstance().bid(userId, projectId, Integer.valueOf(amount));
            Project project = JobOonJa.getInstance().getSuitableProject("1", projectId);
            boolean hasBid = JobOonJa.getInstance().findBid("1", projectId);
            req.setCharacterEncoding("UTF-8");
            req.setAttribute("project", project);
            req.setAttribute("hasBid", hasBid);
            req.getRequestDispatcher("/project.jsp").forward(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
