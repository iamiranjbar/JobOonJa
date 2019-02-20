package Skill;

import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

@JsonObject(asExtraForUnknownProperties = true)
public class Skill {
    @JsonProperty(required = true)
    protected String name;
    @JsonProperty(required = true)
    protected int points;

    public String getName() {
        return name;
    }

    public int getPoint() {
        return points;
    }

}
