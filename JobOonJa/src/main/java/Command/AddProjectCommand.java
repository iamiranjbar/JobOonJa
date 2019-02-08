package Command;

import Project.Project;
import JobOonJa.JobOonJa;

public class AddProjectCommand implements Command{

    private Project project;

    public AddProjectCommand(Project project) {
        this.project = project;
    }

    public void execute() {
        JobOonJa.getInstance().addProject(project);
    }
}
