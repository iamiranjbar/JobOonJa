package models.Project;

import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

import java.util.ArrayList;

@JsonObject(asExtraForUnknownProperties = true)
public class ProjectListDTO {
    @JsonProperty(required = true)
    private ArrayList<Project> projects;

    public ArrayList<Project> getProjects() {
        return projects;
    }
}
