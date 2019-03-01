package models.User;

import models.Exception.*;

import java.util.HashMap;

public class UserManager {
    private static final UserManager instance = new UserManager();
    private HashMap<String, User> repository;

    private UserManager() {
        repository = new HashMap<String, User>();
    }

    public static UserManager getInstance() {
        return instance;
    }

    public void add(String id, User user) throws RedundantUser {
        if (!repository.containsKey(id))
            repository.put(id, user);
        else
            throw new RedundantUser("User has already exist!");
    }

    public User find(String id) throws UserNotFound {
        if(repository.containsKey(id))
            return repository.get(id);
        else
            throw new UserNotFound("User not found!");
    }

}
