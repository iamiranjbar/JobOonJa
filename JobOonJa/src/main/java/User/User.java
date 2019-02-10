package User;

import Skill.Skill;
import java.util.*;

public class User {
    private String username;
    private HashMap<String,Skill> skills;

    public User(String username, HashMap<String, Skill> skills) {
        this.username = username;
        this.skills = skills;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
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
