package ir.ac.ut.models.Bid;

import ir.ac.ut.models.Exception.*;
import ir.ac.ut.models.Project.Project;
import ir.ac.ut.models.Project.ProjectManager;
import ir.ac.ut.models.Skill.*;
import ir.ac.ut.models.User.User;
import ir.ac.ut.models.User.UserManager;

import java.util.ArrayList;

import static ir.ac.ut.models.Skill.SkillManager.haveSkills;

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
            throw new InsufficentBudget("Your needing budget is very high!");
        }
    }

    public ArrayList<Bid> getRepository() {
        return repository;
    }
}
