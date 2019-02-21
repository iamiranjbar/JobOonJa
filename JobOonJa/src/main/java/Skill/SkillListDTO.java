package Skill;

import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

import java.util.ArrayList;

@JsonObject(asExtraForUnknownProperties = true)
public class SkillListDTO {
    @JsonProperty(required = true)
    private ArrayList<Skill> skills;

    public ArrayList<Skill> getSkills() {
        return skills;
    }
}
