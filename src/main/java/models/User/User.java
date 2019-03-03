package models.User;

import models.Skill.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String profilePicURL;
    private HashMap<String, UserSkill> skills;
    private String bio;

    public User() {

    }

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

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public HashMap<String, UserSkill> getSkills() {
        return skills;
    }

    public String getBio() {
        return bio;
    }

    public void addSkill(String skillName){
        this.skills.put(skillName, new UserSkill(skillName, 0));
    }

    public void addSkills(ArrayList<UserSkill> skills){
        for(UserSkill skill:skills) {
            if (!this.skills.containsKey(skill.getName()))
                this.skills.put(skill.getName(), skill);
        }
    }

    public void deleteSkill(String skillName){
        this.skills.remove(skillName);
    }

    public void endorse(String endorser, String skillName){
        UserSkill userSkill = skills.get(skillName);
        userSkill.endorse(endorser);
    }
}
