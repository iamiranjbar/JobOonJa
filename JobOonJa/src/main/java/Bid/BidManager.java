package Bid;

import Project.Project;
import Project.ProjectManager;
import Skill.Skill;
import User.User;
import User.UserManager;

import java.util.ArrayList;
import java.util.HashMap;

public class BidManager {
    private static BidManager instance = null;
    private ArrayList<Bid> repository;

    private BidManager(){
        repository = new ArrayList<Bid>();
    }

    public static BidManager getInstance(){
        if (instance == null)
            instance = new BidManager();
        return  instance;
    }

    public Bid make(String projectTitle, String username, int amount){ // TODO: check null  OR throw exception
        ProjectManager projectManager = ProjectManager.getInstance();
        Project foundProject = projectManager.find(projectTitle);
        UserManager userManager = UserManager.getInstance();
        User foundUser = userManager.find(username);
        return new Bid(foundUser,foundProject,amount);
    }

    public boolean submit(Bid bid){
        if (bid.getAmount() <= bid.getProject().getBudget()) {
            ArrayList<Skill> requirements = bid.getProject().getSkills();
            HashMap<String,Skill> capabillity = bid.getUser().getSkills();
            for (Skill skill: requirements) {
                String key = skill.getName();
                if (!capabillity.containsKey(key) || skill.getPoint() > capabillity.get(key).getPoint())
                    return false;
            }
            repository.add(bid);
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<Bid> getRepository() {
        return repository;
    }
}