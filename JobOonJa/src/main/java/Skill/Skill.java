package Skill;

import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

@JsonObject(asExtraForUnknownProperties = true)
public class Skill {
    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true)
    private  int points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return points;
    }

    public void setPoint(int point) {
        this.points = point;
    }
}
