package Bid;

import com.jsoniter.annotation.JsonObject;
import com.jsoniter.annotation.JsonProperty;

@JsonObject(asExtraForUnknownProperties = true)
public class BidDTO {
    @JsonProperty(required = true)
    private String biddingUser;
    @JsonProperty(required = true)
    private String projectTitle;
    @JsonProperty(required = true)
    private int bidAmount;

    public String getBiddingUser() {
        return biddingUser;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public int getBidAmount() {
        return bidAmount;
    }
}
