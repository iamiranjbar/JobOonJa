package User;

import Skill.Skill;
import java.util.*;

public class User {

    private String name;
    private ArrayList<Skill> skills;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public void  addSkill(Skill skill) {
        this.skills.add(skill);
    }
}
