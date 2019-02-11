package Project;

import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

@JsonObject(asExtraForUnknownProperties = true)
public class AuctionDTO {
    @JsonProperty(required = true)
    private String projectTitle;

    public String getProjectTitle() {
        return projectTitle;
    }
}
