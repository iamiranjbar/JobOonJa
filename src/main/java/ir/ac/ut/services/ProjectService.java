package ir.ac.ut.services;

import ir.ac.ut.models.Exception.InsufficentSkill;
import ir.ac.ut.models.JobOonJa.JobOonJa;
import ir.ac.ut.models.Project.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class ProjectService {
    private JobOonJa jobOonJa = JobOonJa.getInstance();

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Project> > getProjects(){
        try {
            ArrayList<Project> projects = jobOonJa.getSuitableProjects(jobOonJa.getLogInUser());
            return ResponseEntity.ok(projects);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/project/{pid}", method = RequestMethod.GET)
    public ResponseEntity<Project> getProject(@PathVariable(value = "pid") String projectId){
        try {
            Project project = jobOonJa.getSuitableProject(jobOonJa.getLogInUser(), projectId);
            return ResponseEntity.ok(project);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/project/{pid}/bid/{amount}", method = RequestMethod.POST)
    public ResponseEntity bidProject(@PathVariable(value = "pid") String projectId,
                                     @PathVariable(value = "amount") String amount){
        try {
            String userId = jobOonJa.getLogInUser();
            boolean hasBid = jobOonJa.findBid(userId, projectId);
            if (!hasBid)
                jobOonJa.bid(userId, projectId, Integer.valueOf(amount));
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You have already bid for this project...");
            }
            Project project = jobOonJa.getSuitableProject(userId, projectId);
            return ResponseEntity.ok(project);
        } catch (InsufficentSkill insufficentSkill) {
            insufficentSkill.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/project/search/{searchField}", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Project>> search(@PathVariable(value = "searchField") String searchField){
        try {
            ArrayList<Project> projects = jobOonJa.searchProjects(searchField);
            return ResponseEntity.ok(projects);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}