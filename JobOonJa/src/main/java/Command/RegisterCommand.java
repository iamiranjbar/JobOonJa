package Command;

import User.User;
import JobOonJa.JobOonJa;
import Exception.*;

public class RegisterCommand implements Command {
    private User user;

    public RegisterCommand(User user) {
        this.user = user;
    }

    public void execute() throws RedundantUser {
            JobOonJa.getInstance().register(user);
    }
}
