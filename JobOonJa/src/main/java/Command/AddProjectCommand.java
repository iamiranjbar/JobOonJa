package Command;

import Project.Project;
import JobOonJa.JobOonJa;
import Exception.*;

public class AddProjectCommand implements Command{

    private Project project;

    public AddProjectCommand(Project project) {
        this.project = project;
    }

    public void execute() throws RedundantProject {
       JobOonJa.getInstance().addProject(project);
    }
}
