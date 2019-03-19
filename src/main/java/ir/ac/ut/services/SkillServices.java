package ir.ac.ut.services;

import ir.ac.ut.models.Exception.UserNotFound;
import ir.ac.ut.models.JobOonJa.JobOonJa;
import ir.ac.ut.models.Skill.Skill;
import ir.ac.ut.models.Skill.UserSkill;
import ir.ac.ut.models.User.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkillServices {
    private JobOonJa jobOonJa = JobOonJa.getInstance();

    @RequestMapping(value = "/skill/{userId}/{skillName}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUserSkill(@PathVariable(value = "userId") String userId,
                                @PathVariable(value = "skillName") String skillName){
        try {
            jobOonJa.deleteUserSkill(userId,skillName);
            User user = jobOonJa.getUser(userId);
            return ResponseEntity.ok(user);
        } catch (UserNotFound userNotFound) {
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
        } catch (UserNotFound userNotFound) {
            userNotFound.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/skill/{userId}/{skillName}/endorse" , method = RequestMethod.POST)
    public ResponseEntity<User> endorse(@PathVariable(value = "userId") String endorsed,
                                        @PathVariable(value = "skillName") String skillName){
        String loggedInUser = jobOonJa.getLogInUser();
        try {
            jobOonJa.endorse(loggedInUser, endorsed, skillName);
            User user = jobOonJa.getUser(endorsed);
            return ResponseEntity.ok(user);
        } catch (UserNotFound userNotFound) {
            userNotFound.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
