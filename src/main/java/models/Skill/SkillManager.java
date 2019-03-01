package models.Skill;

import models.Exception.*;
import models.Project.Project;
import models.User.User;

import java.util.ArrayList;
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

    public void fill(ArrayList<Skill> skills) {
        for (Skill skill: skills) {
            this.add(skill);
        }
    }

    public Skill find(String name) throws SkillNotFound {
        if(repository.containsKey(name))
            return repository.get(name);
        else
            throw new SkillNotFound("skill not found!");
    }

    public static boolean haveSkills(User user, Project project){
        HashMap<String,UserSkill> capability = user.getSkills();
        ArrayList<Skill> requirements = project.getSkills();
        for (Skill skill: requirements) {
            String key = skill.getName();
            if (!capability.containsKey(key) || skill.getPoint() > capability.get(key).getPoint())
                return false;
        }
        return true;
    }
}
