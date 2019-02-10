package CommandLineInterface;


import Bid.*;
import Command.*;
import Project.*;
import User.*;
import com.jsoniter.JsonIterator;

import java.io.IOException;
import java.util.Scanner;

public class CLI {
    private static final CLI instance = new CLI();
    private static final Scanner reader = new Scanner(System.in);

    public static CLI getInstance() {
        return instance;
    }

    private CLI() { }

    public String getCommand() {
//        Scanner reader = new Scanner(System.in);
        System.out.println("Enter your command: ");
        String command = reader.nextLine();
//        reader.close();
        return command;
    }

    public String[] parseCommand(String command) {
        return command.split("\\s+");
    }

    public Command createCommand(String[] splittedCommand) throws IOException {
        switch (splittedCommand[0]){
            case "register":
                UserDTO userDTO = JsonIterator.deserialize(splittedCommand[1], UserDTO.class);
                UserManager userManager = UserManager.getInstance();
                User user = userManager.makeUserFromDTO(userDTO);
                return new RegisterCommand(user);
            case "addProject":
                Project project = JsonIterator.deserialize(splittedCommand[1], Project.class);
                return new AddProjectCommand(project);
            case "bid":
                BidDTO bidDTO = JsonIterator.deserialize(splittedCommand[1], BidDTO.class);
                BidManager bidManager = BidManager.getInstance();
                Bid bid = bidManager.makeBidFromDTO(bidDTO);
                return new BidCommand(bid);
            case "auction": //TODO: Check bad coding style
                AuctionDTO auctionDTO = JsonIterator.deserialize(splittedCommand[1], AuctionDTO.class);
                return new AuctionCommand(auctionDTO.getProjectTitle());
        }
        return null;
    }
}
