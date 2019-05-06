package ir.ac.ut.services;

import ir.ac.ut.models.JobOonJa.JobOonJa;
import ir.ac.ut.models.User.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class UserService {
    private JobOonJa jobOonJa = JobOonJa.getInstance();

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<User> > getUsers(){
        try {
            ArrayList<User> users = jobOonJa.getUserList(jobOonJa.getLogInUser());
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
    public ResponseEntity<ArrayList<User>> searchUser(@PathVariable(value = "name") String name){
        try {
            ArrayList<User> foundUsers = jobOonJa.searchUser(name);
            return ResponseEntity.ok(foundUsers);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}