package Command;

import User.User;
import JobOonJa.JobOonJa;

public class RegisterCommand implements Command {
    private User user;

    public RegisterCommand(User user) {
        this.user = user;
    }

    public void execute() {
        JobOonJa.getInstance().register(user);
    }
}
