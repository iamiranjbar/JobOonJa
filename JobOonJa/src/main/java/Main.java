import Command.AuctionCommand;
import Command.CommandInterpreter;
import CommandLineInterface.CLI;

public class Main {
    public static void main(String[] args) {
        //CommandInterpreter ci = new CommandInterpreter();
        //ci.addCommand(new AuctionCommand("ali");
        //ci.runAll();
        CLI cli = CLI.getInstance();
        String s = cli.getCommand();
        cli.parseCommand(s);
    }
}
