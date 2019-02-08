package JobOonJa;

import Bid.Bid;
import Bid.BidManager;
import Project.Project;
import Project.ProjectManager;
import User.User;
import User.UserManager;

public class JobOonJa {

    private static final JobOonJa ourInstance = new JobOonJa();
    private UserManager userManager;
    private ProjectManager projectManager;
    private BidManager bidManager;

    public static JobOonJa getInstance() {
        return ourInstance;
    }

    private JobOonJa() {
    }

    public void register(User user) {

    }

    public void auction(String projectTitle) {

    }

    public void addProject(Project project) {

    }

    public void bid(Bid bid) {

    }
}
