package Command;

import JobOonJa.JobOonJa;

public class AuctionCommand implements Command {
    private String projectTitle‬‬;

    public AuctionCommand(String projectTitle‬‬) {
        this.projectTitle‬‬ = projectTitle‬‬;
    }

    public void execute() {
        JobOonJa.getInstance().auction(projectTitle‬‬);
    }
}
