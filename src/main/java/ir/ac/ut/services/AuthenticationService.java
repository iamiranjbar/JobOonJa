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
    private int maxId = 10;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity signup(@RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName,
                                 @RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("title") String title,
                                 @RequestParam("imageLink") String imageLink,
                                 @RequestParam("bio") String bio){
        try {
            jobOonJa.register(new User(String.valueOf(maxId++), firstName, lastName, username, password, title,
                    imageLink, bio));
            return (ResponseEntity) ResponseEntity.ok(Jwt.createJWT(String.valueOf(maxId-1), "JobOonJa",
                    "auth-signup", 111223));
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Signup is failed.");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity signup(@RequestParam("username") String username,
                                 @RequestParam("password") String password) {
        try {
            String id = jobOonJa.findRegisterUserByUsername(username, password);
            return (ResponseEntity) ResponseEntity.ok(Jwt.createJWT(String.valueOf(id), "JobOonJa",
                    "auth-login", 111223));
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login is failed. username or password is invalid.");
        }

    }

}
