package Bid;

import Project.Project;
import Project.ProjectManager;
import Skill.*;
import User.User;
import User.UserManager;
import Exception.*;

import java.util.ArrayList;
import java.util.HashMap;

import static Skill.SkillManager.haveSkills;

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

    public void submit(Bid bid) throws InsufficentSkill, InsufficentBudget {
        if (bid.getAmount() <= bid.getProject().getBudget()) {
            if (haveSkills(bid.getUser(),bid.getProject()))
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
