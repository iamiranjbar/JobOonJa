package User;

import Skill.*;
import java.util.*;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String profilePicURL;
    private HashMap<String, UserSkill> skills;
    private String bio;

    public User(String id, String firstName, String lastName, String jobTitle, String profilePicURL,
                HashMap<String, UserSkill> skills, String bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.profilePicURL = profilePicURL;
        this.skills = skills;
        this.bio = bio;
    }

    public String getName() {
        return id;
    }

    public HashMap<String,UserSkill> getSkills() {
        return skills;
    }

}
