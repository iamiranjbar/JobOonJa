package servlets;

import models.JobOonJa.JobOonJa;
import models.Skill.UserSkill;
import models.User.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
            users.add(new User("1","علی","شریف‌زاده","برنامه‌نویس وب", "../../../img/kami.jpg",
                    this.makeUserSkill(),"روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت"));
            req.setAttribute("users",users);
            req.getRequestDispatcher("users.jsp").forward(req, resp);
        } else {
            String[] pathParts = pathInfo.split("/");
            String part1 = pathParts[1];
            System.out.println(part1);
        }
    }

    private HashMap<String, UserSkill> makeUserSkill(){
        HashMap<String,UserSkill> skills = new HashMap<>();
        skills.put("HTML",new UserSkill("HTML",5));
        skills.put("Javascript",new UserSkill("Javascript",4));
        skills.put("C++",new UserSkill("C++",2));
        skills.put("Java",new UserSkill("Java",3));
        return skills;
    }
}