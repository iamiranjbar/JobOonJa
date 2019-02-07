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
}
