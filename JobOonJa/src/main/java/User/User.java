package User;

import Skill.Skill;
import java.util.*;

public class User {

    private String name;
    private HashMap<String,Skill> skills;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String,Skill> getSkills() {
        return skills;
    }

    public void setSkills(HashMap<String,Skill> skills) {
        this.skills = skills;
    }

    public void  addSkill(Skill skill) {
        this.skills.put(skill.getName(),skill);
    }
}
