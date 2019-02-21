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

    public void add(String id, Project project) throws RedundantProject {
        if(!repository.containsKey(id))
            repository.put(id,project);
        else
            throw new RedundantProject("Project has already exist!");
    }

    public Project find(String id) throws ProjectNotFound {
        if(repository.containsKey(id))
            return repository.get(id);
        else
            throw new ProjectNotFound("Project not found!");
    }

    public Map<String, Project> getRepository() {
        return repository;
    }
}
