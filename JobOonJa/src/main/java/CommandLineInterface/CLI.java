package CommandLineInterface;


import Command.*;
import Project.Project;
import User.*;
import com.dslplatform.json.DslJson;

import java.io.IOException;
import java.util.Scanner;

public class CLI {
    private static final CLI instance = new CLI();
    private final DslJson<Object> dslJson = new DslJson<>();

    public static CLI getInstance() {
        return instance;
    }

    private CLI() { }

    public String getCommand() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter your command: ");
        String command = reader.nextLine();
        reader.close();
        return command;
    }

    public String[] parseCommand(String command) {
        return command.split("\\s+");
    }

    public Command createCommand(String[] splittedCommand) throws IOException {
        byte[] byteStream = splittedCommand[1].getBytes();
        switch (splittedCommand[0]){
            case "register":
                UserDTO userDTO = dslJson.deserialize(UserDTO.class, byteStream, byteStream.length);
                UserManager userManager = UserManager.getInstance();
                User user = userManager.makeUserFromDTO(userDTO);
                return new RegisterCommand(user);
                break;
            case "addProject":
                Project project = dslJson.deserialize(Project.class,byteStream, byteStream.length);
                return new AddProjectCommand(project);
                break;
            case "bid":

                break;
            case "auction":

                break;
        }
    }
}
