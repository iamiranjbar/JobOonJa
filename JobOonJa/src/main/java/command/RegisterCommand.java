package command;

import user.User;

public class RegisterCommand implements Command {
    private User user;

    public RegisterCommand(User user) {
        this.user = user;
    }

    public void execute() {
        
    }
}
