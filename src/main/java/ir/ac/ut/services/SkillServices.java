package ir.ac.ut.services;

import io.jsonwebtoken.Claims;
import ir.ac.ut.models.Exception.UserNotFound;
import ir.ac.ut.models.JobOonJa.JobOonJa;
import ir.ac.ut.models.Skill.Skill;
import ir.ac.ut.models.Skill.UserSkill;
import ir.ac.ut.models.User.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class SkillServices {
    private JobOonJa jobOonJa = JobOonJa.getInstance();

    @RequestMapping(value = "/skill/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Skill> > getUserSkills(@PathVariable(value = "userId") String userId){
        try {
            return ResponseEntity.ok(jobOonJa.getUserAbilities(userId));
        } catch (UserNotFound | SQLException userNotFound) {
            userNotFound.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/skill/{userId}/{skillName}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUserSkill(@PathVariable(value = "userId") String userId,
                                @PathVariable(value = "skillName") String skillName){
        try {
            jobOonJa.deleteUserSkill(userId,skillName);
            User user = jobOonJa.getUser(userId);
            return ResponseEntity.ok(user);
        } catch (UserNotFound | SQLException userNotFound) {
            userNotFound.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/skill/{userId}/{skillName}", method = RequestMethod.PUT)
    public ResponseEntity<User> addUserSkill(@PathVariable(value = "userId") String userId,
                                             @PathVariable(value = "skillName") String skillName){
        try {
            jobOonJa.addSkillToUser(userId, skillName);
            User user = jobOonJa.getUser(userId);
            return ResponseEntity.ok(user);
        } catch (UserNotFound | SQLException userNotFound) {
            userNotFound.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/skill/{userId}/{skillName}/endorse" , method = RequestMethod.POST)
    public ResponseEntity<User> endorse(@PathVariable(value = "userId") String endorsed,
                                        @PathVariable(value = "skillName") String skillName,
                                        @RequestAttribute Claims claims){
        String loggedInUser = claims.getId();
        try {
            jobOonJa.endorse(loggedInUser, endorsed, skillName);
            User user = jobOonJa.getUser(endorsed);
            return ResponseEntity.ok(user);
        } catch (UserNotFound | SQLException userNotFound) {
            userNotFound.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
