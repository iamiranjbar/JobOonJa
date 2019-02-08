package Project;

import Skill.Skill;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Project {
    private String title;
    private ArrayList<Skill> skills;
    private int budget;

    public Project(String title, ArrayList<Skill> skills, int budget){
        this.title = title;
        this.skills = new ArrayList<Skill>(skills);
        this.budget = budget;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
