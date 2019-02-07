package User;

import  java.util.*;

public class UserManager {
    private static final UserManager ourInstance = new UserManager();
    private HashMap<String, User> hashMap;

    private UserManager() {
    }

    public static UserManager getInstance() {
        return ourInstance;
    }

    public boolean find(String key) {
        return hashMap.containsKey(key);
    }

    public void add(String key, User user) {
        if (!find(key)) {
            hashMap.put(key, user);
        }
    }
}
