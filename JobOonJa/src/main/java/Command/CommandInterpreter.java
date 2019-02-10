package Command;

import Exception.*;

import java.util.ArrayList;

public class CommandInterpreter {

    private ArrayList<Command> commands;

    public CommandInterpreter() {
        commands = new ArrayList<Command>();
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void run() throws RedundantUser, RedundantProject {
        commands.get(commands.size() - 1).execute();
        commands.remove(commands.size() - 1);
    }

    public void  runAll() throws RedundantUser, RedundantProject {
        for (Command command : commands) {
            command.execute();
        }

        commands.clear();
    }
}
