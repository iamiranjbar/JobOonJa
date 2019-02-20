package Skill;

import Exception.*;

import java.util.HashMap;
import java.util.Map;

public class SkillManager {
    private static final SkillManager instance = new SkillManager();
    private Map<String,Skill> repository;

    private SkillManager() {
        repository = new HashMap<String, Skill>();
    }

    public static SkillManager getInstance() {
        return instance;
    }

    public void add(Skill skill) {
        if(!repository.containsKey(skill.getName()))
            repository.put(skill.getName(),skill);
    }

    public Skill find(String name) throws SkillNotFound {
        if(repository.containsKey(name))
            return repository.get(name);
        else
            throw new SkillNotFound("skill not found!");
    }
}
