package User;

import Skill.Skill;

import  java.util.*;

public class UserManager {
    private static UserManager instance = null;
    private HashMap<String, User> repository;

    private UserManager() {
        repository = new HashMap<String, User>();
    }

    public static UserManager getInstance() {
        if (instance == null)
            instance = new UserManager();
        return instance;
    }

    public void add(String username, User user) {
        if (!repository.containsKey(username)) {
            repository.put(username, user);
        }
        // TODO: handel username that use before.
    }

    public User find(String username) {
        if(repository.containsKey(username))
            return repository.get(username);
        else
            return null;
    }

    public User makeUserFromDTO(UserDTO userDTO) {
        String username = userDTO.getUsername();
        ArrayList<Skill> temp = userDTO.getSkills();
        HashMap<String,Skill> skills = new HashMap<>();
        for (Skill skill:temp)
            skills.put(skill.getName(), skill);
        return new User(username,skills);
    }
}
