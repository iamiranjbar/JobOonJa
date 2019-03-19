package ir.ac.ut.models.Skill;

import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

@JsonObject(asExtraForUnknownProperties = true)
public class Skill {
    @JsonProperty(required = true)
    protected String name;
    protected int point;

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }

}
