package Bid;

import Project.Project;
import Project.ProjectManager;
import Skill.*;
import User.User;
import User.UserManager;
import Exception.*;

import java.util.ArrayList;
import java.util.HashMap;

public class BidManager {
    private static final BidManager instance = new BidManager();
    private ArrayList<Bid> repository;

    private BidManager(){
        repository = new ArrayList<Bid>();
    }

    public static BidManager getInstance(){
        return  instance;
    }

    public Bid makeBidFromDTO(BidDTO bidDTO) throws ProjectNotFound, UserNotFound {
        ProjectManager projectManager = ProjectManager.getInstance();
        Project foundProject = projectManager.find(bidDTO.getProjectTitle());
        UserManager userManager = UserManager.getInstance();
        User foundUser = userManager.find(bidDTO.getBiddingUser());
        return new Bid(foundUser,foundProject,bidDTO.getBidAmount());
    }

    private boolean haveSkills(ArrayList<Skill> requirements, HashMap<String,UserSkill> capability){
        for (Skill skill: requirements) {
            String key = skill.getName();
            if (!capability.containsKey(key) || skill.getPoint() > capability.get(key).getPoint())
                return false;
        }
        return true;
    }

    public void submit(Bid bid) throws InsufficentSkill, InsufficentBudget {
        if (bid.getAmount() <= bid.getProject().getBudget()) {
            ArrayList<Skill> requirements = bid.getProject().getSkills();
            HashMap<String,UserSkill> capability = bid.getUser().getSkills();
            if (haveSkills(requirements,capability))
                repository.add(bid);
            else
                throw new InsufficentSkill("You don't have enough skills!");
        }
        else{
            throw new InsufficentBudget("Your budget is too low!");
        }
    }

    public ArrayList<Bid> getRepository() {
        return repository;
    }
}
