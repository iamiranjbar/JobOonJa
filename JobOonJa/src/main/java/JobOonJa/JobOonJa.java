package JobOonJa;

import Bid.*;
import Project.*;
import Skill.*;
import User.*;
import Exception.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobOonJa {

    private static final JobOonJa ourInstance = new JobOonJa();
    private UserManager userManager;
    private ProjectManager projectManager;
    private BidManager bidManager;
    private SkillManager skillManager;

    public static JobOonJa getInstance() {
        return ourInstance;
    }

    private JobOonJa() {
        userManager = UserManager.getInstance();
        projectManager = ProjectManager.getInstance();
        bidManager = BidManager.getInstance();
        skillManager = SkillManager.getInstance();
    }

    private int goalFunction(Bid bid) {
        int result = 0;
        ArrayList<Skill> jobSkills = bid.getProject().getSkills();
        HashMap<String, UserSkill> userSkills = bid.getUser().getSkills();
        for (Skill skill : jobSkills) {
            String key = skill.getName();
            result += 10000 * Math.pow((userSkills.get(key).getPoint() - skill.getPoint()), 2);
        }
        result += (bid.getProject().getBudget() - bid.getAmount());
        return result;
    }

    public void register(User user) throws RedundantUser {
        userManager.add(user.getId(), user);
    }

    public void auction(String projectTitle) {
        ArrayList<Bid> bids = bidManager.getRepository();
        int maximum = 0;
        User selected = null;
        for (Bid bid : bids) {
            if (bid.getProject().getTitle().equals(projectTitle)
                    && goalFunction(bid) > maximum) {
                maximum = goalFunction(bid);
                selected = bid.getUser();
            }
        }
        if (selected != null) {
            System.out.printf("winner: %s\n", selected.getFirstName());
        } else {
            System.out.println("we do not have any bid for this project.");
        }
    }

    public void addProject(Project project) throws RedundantProject {
        projectManager.add(project.getTitle(), project);
    }

    public void bid(Bid bid) throws InsufficentBudget, InsufficentSkill {
        bidManager.submit(bid);
    }

    public User getUser(String id) throws UserNotFound {
        return userManager.find(id);
    }

    public Project getProject(String id) throws ProjectNotFound {
        return projectManager.find(id);
    }

    public Map<String, Project> getProjects() {
        return projectManager.getRepository();
    }

    public void run() {

    }

    public void addSkills(ArrayList<Skill> skills) {
        skillManager.addList(skills);
    }

    public void addProjects(ArrayList<Project> projects) {
        projectManager.addList(projects);
    }
}
