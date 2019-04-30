package ir.ac.ut.models.Bid;

import ir.ac.ut.models.Project.Project;
import ir.ac.ut.models.User.User;

public class Bid {
    private ir.ac.ut.models.User.User user;
    private Project project;
    private int amount;

    public Bid() {

    }

    public Bid(User user, Project project, int amount){
        this.user = user;
        this.project = project;
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public Project getProject() {
        return project;
    }

    public int getAmount() {
        return amount;
    }

}
