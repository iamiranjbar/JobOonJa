package ir.ac.ut.models.Project;

import ir.ac.ut.models.Bid.Bid;
import ir.ac.ut.models.Exception.*;
import ir.ac.ut.models.User.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectManager {
    private static final ProjectManager instance = new ProjectManager();
    private HashMap<String,Project> repository;

    private ProjectManager() {
        repository = new HashMap<String, Project>();
    }

    public static ProjectManager getInstance() {
        return instance;
    }

    public void add(String id, Project project) throws RedundantProject {
        if(!repository.containsKey(id))
            repository.put(id,project);
        else
            throw new RedundantProject("Project has already exist!");
    }

    public void fill(ArrayList<Project> projects) throws RedundantProject {
        for (Project project: projects) {
            this.add(project.getId(), project);
        }
    }

    public Project find(String id) throws ProjectNotFound {
        if(repository.containsKey(id))
            return repository.get(id);
        else
            throw new ProjectNotFound("Project not found!");
    }

    public HashMap<String, Project> getRepository() {
        return repository;
    }
}
