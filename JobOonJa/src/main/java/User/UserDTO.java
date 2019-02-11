package User;

import Skill.Skill;
import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

import java.util.ArrayList;

@JsonObject(asExtraForUnknownProperties = true)
public class UserDTO {
    @JsonProperty(required = true)
    private String username;
    @JsonProperty(required = true)
    private ArrayList<Skill> skills;

    public String getUsername() {
        return username;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }
}
