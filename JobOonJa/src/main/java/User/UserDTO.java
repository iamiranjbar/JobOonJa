package User;

import Skill.Skill;

import java.util.ArrayList;

public class UserDTO {
    private String username;
    private ArrayList<Skill> skills;

    public String getUsername() {
        return username;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }
}
