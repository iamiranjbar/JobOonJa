package Command;

import Bid.Bid;
import JobOonJa.JobOonJa;
import Exception.*;

public class BidCommand implements Command {

    private Bid bid;

    public BidCommand(Bid bid) {
        this.bid = bid;
    }

    public void execute() throws BadInput {
        JobOonJa.getInstance().bid(bid);
    }
}
