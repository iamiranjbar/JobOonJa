package ir.ac.ut.models.Project;

import ir.ac.ut.models.Bid.Bid;
import ir.ac.ut.models.Skill.Skill;
import ir.ac.ut.models.User.User;
import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

import java.util.ArrayList;

@JsonObject(asExtraForUnknownProperties = true)
public class Project {
    @JsonProperty(required = true)
    private String id;
    @JsonProperty(required = true)
    private String title;
    @JsonProperty(required = true)
    private String description;
    @JsonProperty(required = true)
    private String imageUrl;
    @JsonProperty(required = true)
    private ArrayList<Skill> skills;
    @JsonProperty(required = true)
    private int budget;
    @JsonProperty(required = true)
    private long deadline;
    private User winner;
    private ArrayList<Bid> bids;

    public Project() {

    }

    public Project(String id, String title, String description, String imageURL, ArrayList<Skill> skills,
                   ArrayList<Bid> bids, int budget, long deadline, User winner) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageURL;
        this.skills = skills;
        this.bids = bids;
        this.budget = budget;
        this.deadline = deadline;
        this.winner = winner;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public int getBudget() {
        return budget;
    }

    public long getDeadline() {
        return deadline;
    }

    public User getWinner() {
        return winner;
    }
}
