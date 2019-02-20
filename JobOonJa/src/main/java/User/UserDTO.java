package User;

import Skill.*;
import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;

@JsonObject(asExtraForUnknownProperties = true)
public class UserDTO {
    @JsonProperty(required = true)
    private String id;
    @JsonProperty(required = true)
    private String firstName;
    @JsonProperty(required = true)
    private String lastName;
    @JsonProperty(required = true)
    private String jobTitle;
    @JsonProperty(required = true)
    private String profilePicURL;
    @JsonProperty(required = true)
    private ArrayList<UserSkill> skills;
    @JsonProperty(required = true)
    private String bio;
}
