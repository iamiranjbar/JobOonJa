package Command;

import java.util.ArrayList;

public class CommandInterpreter {

    private ArrayList<Command> commands;

    public CommandInterpreter() {
        commands = new ArrayList<Command>();
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void run() {
        commands.get(commands.size() - 1).execute();
        commands.remove(commands.size() - 1);
    }

    public void  runAll() {
        for (Command command : commands) {
            command.execute();
        }

        commands.clear();
    }
}
