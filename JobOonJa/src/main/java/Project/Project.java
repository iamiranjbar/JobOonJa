package Project;

import Skill.Skill;
import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

import java.lang.reflect.Array;
import java.util.ArrayList;

@JsonObject(asExtraForUnknownProperties = true)
public class Project {
    @JsonProperty(required = true)
    private String title;
    @JsonProperty(required = true)
    private ArrayList<Skill> skills;
    @JsonProperty(required = true)
    private int budget;

    public Project() {}

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
