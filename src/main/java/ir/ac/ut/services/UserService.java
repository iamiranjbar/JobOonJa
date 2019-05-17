package ir.ac.ut.services;

import ir.ac.ut.models.JobOonJa.JobOonJa;
import ir.ac.ut.models.User.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Claims;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class UserService {
    private JobOonJa jobOonJa = JobOonJa.getInstance();

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<User> > getUsers(@RequestAttribute Claims claims){
        try {
            ArrayList<User> users = jobOonJa.getUserList(claims.getId());
            return ResponseEntity.ok(users);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/user/{uid}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable(value = "uid") String userId){
        try {
            User user = jobOonJa.getUser(userId);
            return ResponseEntity.ok(user);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/user/search/{name}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<User>> searchUser(@PathVariable(value = "name") String name,@RequestAttribute Claims claims){
        try {
            ArrayList<User> foundUsers = jobOonJa.searchUser(name);
            for (int i = 0; i < foundUsers.size(); i++) {
                if (foundUsers.get(i).getId().equals(claims.getId()))
                    foundUsers.remove(i);
            }
            return ResponseEntity.ok(foundUsers);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}