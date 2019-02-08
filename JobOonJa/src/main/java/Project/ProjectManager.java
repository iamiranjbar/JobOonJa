package Project;

import Skill.Skill;

import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;

public class ProjectManager {
    private static ProjectManager instance = null;
    private Map<String,Project> repository;

    private ProjectManager() {
        repository = new HashMap<String, Project>();
    }

    public static ProjectManager getInstance() {
        if (instance == null)
            instance = new ProjectManager();
        return instance;
    }

    public void add(String title, Project project){
        if(!repository.containsKey(title))
            repository.put(title,project);
        // TODO: handel title that use before.
    }

    public Project find(String title){
        if(repository.containsKey(title))
            return repository.get(title);
        else
            return null;
    }
}
