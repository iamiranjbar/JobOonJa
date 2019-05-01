package ir.ac.ut.models.User;

import ir.ac.ut.models.Skill.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String jobTitle;
    private String profilePicURL;
    private HashMap<String, UserSkill> skills;
    private String bio;

    public User() {

    }

    public User(String id, String firstName, String lastName, String username, String password,  String jobTitle, String profilePicURL,
                HashMap<String, UserSkill> skills, String bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.setUsername(username);
        this.setPassword(password);
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
