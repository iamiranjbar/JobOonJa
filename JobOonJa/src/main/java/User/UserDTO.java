package User;

import Skill.*;
import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;

@JsonObject(asExtraForUnknownProperties = true)
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String profilePicURL;
    private String bio;
}
