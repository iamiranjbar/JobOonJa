import Command.AuctionCommand;
import Command.CommandInterpreter;

public class Main {
    public static void main(String[] args) {
        CommandInterpreter ci = new CommandInterpreter();
        ci.addCommand(new AuctionCommand("ali"));
        ci.runAll();
    }
}
