package ir.ac.ut.models.User;

import ir.ac.ut.models.Exception.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class UserManager {
    private static final UserManager instance = new UserManager();
    private HashMap<String, User> repository;

    private UserManager() {
        repository = new HashMap<>();
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

    public ArrayList<User> getUserList() {
        Collection<User> users = repository.values();
        return new ArrayList<>(users);
    }

}
