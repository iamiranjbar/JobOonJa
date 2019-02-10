package Command;

import Exception.*;

public interface Command {
    void execute() throws RedundantUser, RedundantProject;
}
