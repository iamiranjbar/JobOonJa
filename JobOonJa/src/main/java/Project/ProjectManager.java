package Project;

import Exception.*;

import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;

public class ProjectManager {
    private static final ProjectManager instance = new ProjectManager();
    private Map<String,Project> repository;

    private ProjectManager() {
        repository = new HashMap<String, Project>();
    }

    public static ProjectManager getInstance() {
        return instance;
    }

    public void add(String title, Project project) throws RedundantProject {
        if(!repository.containsKey(title))
            repository.put(title,project);
        else
            throw new RedundantProject("Project has already exist!");
    }

    public Project find(String title) throws ProjectNotFound {
        if(repository.containsKey(title))
            return repository.get(title);
        else
            throw new ProjectNotFound("Project not found!");
    }
}
