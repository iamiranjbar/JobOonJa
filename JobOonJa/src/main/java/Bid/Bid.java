package Bid;

import Project.Project;
import User.User;

public class Bid {
    private User user;
    private Project project;
    private int price;

    public Bid(User user, Project project, int price){
        this.user = user;
        this.project = project;
        this.price = price;
    }
}
