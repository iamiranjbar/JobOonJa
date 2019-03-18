package servlets;

import models.JobOonJa.JobOonJa;
import models.Project.Project;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;

@RestController
public class ProjectService {
    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ArrayList<Project> getProjects(){
        try {
            return JobOonJa.getInstance().getSuitableProjects(JobOonJa.getInstance().getLogInUser());
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/project/{pid}", method = RequestMethod.GET)
    public Project getProject(@PathVariable(value = "pid") String projectId){
        System.out.println(projectId);
        try {
            return JobOonJa.getInstance().getSuitableProject(JobOonJa.getInstance().getLogInUser(),
                    projectId);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/project/{pid}/bid", method = RequestMethod.PUT)
    public int bidProject(@PathVariable(value = "pid") String projectId,
                          @RequestBody final BidRequest request){
        System.out.println(projectId);
        try {
            boolean hasBid = JobOonJa.getInstance().findBid(JobOonJa.getInstance().getLogInUser(), projectId);
            if (!hasBid)
                JobOonJa.getInstance().bid(JobOonJa.getInstance().getLogInUser(),
                        projectId, Integer.valueOf(request.getAmount()));
            return 200;
        } catch (Exception exception) {
            exception.printStackTrace();
            return 500;
        }
    }

//    @RequestMapping(value = "/project/hasbid", method = RequestMethod.GET)
//    public Boolean checkBidProject(@PathVariable(value = "pid") String projectId){
//        System.out.println(projectId);
//        try {
//            return JobOonJa.getInstance().findBid(JobOonJa.getInstance().getLogInUser(), projectId);
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            return null;
//        }
//    }

}

class BidRequest implements Serializable{
    int amount;
    String userid;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}