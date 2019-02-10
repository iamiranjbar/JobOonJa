import Command.AuctionCommand;
import Command.CommandInterpreter;
import CommandLineInterface.CLI;
import JobOonJa.JobOonJa;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JobOonJa jobOonJa = JobOonJa.getInstance();
        jobOonJa.run();
    }
}
