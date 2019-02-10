package JobOonJa;

import Bid.*;
import Command.CommandInterpreter;
import CommandLineInterface.CLI;
import Project.*;
import Skill.Skill;
import User.*;
import Exception.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class JobOonJa {

    private static final JobOonJa ourInstance = new JobOonJa();
    private UserManager userManager;
    private ProjectManager projectManager;
    private BidManager bidManager;

    public static JobOonJa getInstance() {
        return ourInstance;
    }

    private JobOonJa() {
        userManager = UserManager.getInstance();
        projectManager = ProjectManager.getInstance();
        bidManager = BidManager.getInstance();
    }

    private int goalFunction(Bid bid) {
        int result = 0;
        ArrayList<Skill> jobSkills = bid.getProject().getSkills();
        HashMap<String, Skill> userSkills = bid.getUser().getSkills();
        for (Skill skill : jobSkills) {
            String key = skill.getName();
            result += 10000 * Math.pow((userSkills.get(key).getPoint() - skill.getPoint()), 2);
        }
        result += (bid.getProject().getBudget() - bid.getAmount());
        return result;
    }

    public void register(User user) throws RedundantUser {
        userManager.add(user.getName(), user);
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
            System.out.printf("winner: %s\n", selected.getName());
        } else {
            System.out.println("we do not have any bid for this project.");
        }
    }

    public void addProject(Project project) throws RedundantProject {
        projectManager.add(project.getTitle(), project);
    }

    public void bid(Bid bid) {
        if (!bidManager.submit(bid)) {
            // TODO: handle bad bid for submitting.
        }
    }

    public void run() {
        CLI cli = CLI.getInstance();
        CommandInterpreter ci = new CommandInterpreter();
        while (true) { //TODO: Better condition
            try{
                String command = cli.getCommand();
                String[] splittedCommands = cli.parseCommand(command);
                ci.addCommand(cli.createCommand(splittedCommands));
                ci.run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
