package ir.ac.ut.services;

import ir.ac.ut.jwt.Jwt;
import ir.ac.ut.models.JobOonJa.JobOonJa;
import ir.ac.ut.models.User.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class AuthenticationService {
    private JobOonJa jobOonJa = JobOonJa.getInstance();
    private int maxId = 50;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity signup(@RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName,
                                 @RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("title") String title,
                                 @RequestParam("imageLink") String imageLink,
                                 @RequestParam("bio") String bio){
        try {
            if (!jobOonJa.usernameExist(username)) {
                jobOonJa.register(new User(String.valueOf(maxId++), firstName, lastName, username, password, title,
                        imageLink, bio));
                return ResponseEntity.status(HttpStatus.OK).body(null);
            } else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user already exist!");
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Signup is failed.");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestParam("username") String username,
                                 @RequestParam("password") String password) {
        try {
            String id = jobOonJa.findRegisterUserByUsername(username, password);
            String jwt = Jwt.createJWT(id, "JobOonJa", "auth-login", 600000);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login is failed. username or password is invalid.");
        }

    }

    @RequestMapping(value = "/validate/{username}", method = RequestMethod.GET)
    public ResponseEntity checkUsernameExistance(@PathVariable("username") String username) {
        try {
            boolean result = jobOonJa.usernameExist(username);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("username is invalid.");
        }

    }

}
