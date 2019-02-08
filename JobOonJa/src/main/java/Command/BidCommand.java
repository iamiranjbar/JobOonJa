package Command;

import Bid.Bid;
import JobOonJa.JobOonJa;

public class BidCommand implements Command {

    private Bid bid;

    public BidCommand(Bid bid) {
        this.bid = bid;
    }

    public void execute() {
        JobOonJa.getInstance().bid(bid);
    }
}
