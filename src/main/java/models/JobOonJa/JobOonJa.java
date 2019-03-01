package models.JobOonJa;

import models.Bid.*;
import models.Exception.*;
import models.Project.*;
import models.Skill.*;
import models.User.*;

import java.util.ArrayList;
import java.util.HashMap;

import static models.Skill.SkillManager.haveSkills;

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

    public Project getSuitableProject(String userId, String id) throws ProjectNotFound, InsufficentSkill, UserNotFound {
        User user = userManager.find(userId);
        Project project = projectManager.find(id);
        if (haveSkills(user, project))
            return project;
        else throw new InsufficentSkill("You don't have nough skills to see this project!");

    }

    public ArrayList<Project> getSuitableProjects(String userId) throws UserNotFound {
        User user = userManager.find(userId);
        HashMap<String,Project> repo = projectManager.getRepository();
        ArrayList<Project> projects = new ArrayList<>();
        for(HashMap.Entry<String, Project> entry : repo.entrySet()) {
            Project project = entry.getValue();
            if(haveSkills(user, project))
                projects.add(project);
        }
        return projects;
    }

    public ArrayList<User> getUserList(String userId){
        ArrayList<User> userList = userManager.getUserList();
        for(int i=0; i<userList.size(); i++)
            if (userList.get(i).getId().equals(userId))
                userList.remove(i);
        return userList;
    }

    public void addSkills(ArrayList<Skill> skills) {
        skillManager.fill(skills);
    }

    public void addProjects(ArrayList<Project> projects) throws RedundantProject {
        projectManager.fill(projects);
    }
}
