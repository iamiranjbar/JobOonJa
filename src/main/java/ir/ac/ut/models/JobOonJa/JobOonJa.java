package ir.ac.ut.models.JobOonJa;

import com.jsoniter.JsonIterator;
import com.jsoniter.fuzzy.StringFloatDecoder;
import ir.ac.ut.models.Bid.*;
import ir.ac.ut.models.Exception.*;
import ir.ac.ut.models.Project.*;
import ir.ac.ut.models.Skill.*;
import ir.ac.ut.models.User.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import static ir.ac.ut.models.Skill.SkillManager.haveSkills;

public class JobOonJa {

    private static JobOonJa ourInstance;

    static {
        try {
            ourInstance = new JobOonJa();
        } catch (RedundantUser | RedundantProject | IOException e) {
            e.printStackTrace();
        }
    }

    private UserManager userManager;
    private ProjectManager projectManager;
    private BidManager bidManager;
    private SkillManager skillManager;

    public static JobOonJa getInstance() {
        return ourInstance;
    }

    private JobOonJa() throws RedundantUser, RedundantProject, IOException {
        userManager = UserManager.getInstance();
        projectManager = ProjectManager.getInstance();
        bidManager = BidManager.getInstance();
        skillManager = SkillManager.getInstance();
        this.initialize();
    }

    private HashMap<String,UserSkill> makeUserSkill(){
        HashMap<String,UserSkill> skills = new HashMap<>();
        skills.put("HTML",new UserSkill("HTML",5));
        skills.put("Javascript",new UserSkill("Javascript",4));
        skills.put("C++",new UserSkill("C++",2));
        skills.put("Java",new UserSkill("Java",3));
        return skills;
    }

    private User makeAdminUser(){
        this.makeUserSkill();
        return new User("1","علی","شریف‌زاده","برنامه‌نویس وب", "../../../img/kami.jpg",
                this.makeUserSkill(),"روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت");
    }

    private User makeUser(String id, String firstName, String lastName, String jobTitle, String bio){
        this.makeUserSkill();
        return new User(id,firstName,lastName,jobTitle, "",this.makeUserSkill(),bio);
    }

    private String extractGetData(HttpGet httpGet) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpGet);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line;
        StringBuilder total = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }

    private void getSkills() throws IOException {
        HttpGet httpGet = new HttpGet("http://142.93.134.194:8000/joboonja/skill");
        String total = this.extractGetData(httpGet);
        SkillListDTO skillList = JsonIterator.deserialize("{\"skills\":" + total + "}", SkillListDTO.class);
        this.addSkills(skillList.getSkills());
    }

    private void getProjects() throws IOException, RedundantProject {
        HttpGet httpGet = new HttpGet("http://142.93.134.194:8000/joboonja/project");
        String total = this.extractGetData(httpGet);
        ProjectListDTO projectList = JsonIterator.deserialize("{\"projects\":" + total + "}", ProjectListDTO.class);
        this.addProjects(projectList.getProjects());
    }

    private void initialize() throws RedundantUser, IOException, RedundantProject {
        User user = this.makeAdminUser();
        this.register(user);
        this.register(this.makeUser("2", "ali", "edalat", "student", "pro"));
        this.register(this.makeUser("3", "amir", "ranjber", "student", "pro"));
        this.getSkills();
        this.getProjects();
    }

    public String getLogInUser() {
        return "1";
    }


    private int goalFunction(Bid bid) {
        int result = 0;
        ArrayList<Skill> jobSkills = bid.getProject().getSkills();
        HashMap<String, UserSkill> userSkills = bid.getUser().getSkills();
        for (Skill skill : jobSkills) {
            String key = skill.getName();
            result += 10000 * Math.pow((userSkills.get(key).getPoint() - skill.getPoint()), 2);
        }
        result += (bid.getProject().getBudget() - bid.getAmount());
        return result;
    }

    public void register(User user) throws RedundantUser {
        userManager.add(user.getId(), user);
    }

    public void auction(String projectTitle) {
        ArrayList<Bid> bids = bidManager.getRepository();
        int maximum = 0;
        User selected = null;
        for (Bid bid : bids) {
            if (bid.getProject().getTitle().equals(projectTitle)
                    && goalFunction(bid) > maximum) {
                maximum = goalFunction(bid);
                selected = bid.getUser();
            }
        }
        if (selected != null) {
            System.out.printf("winner: %s\n", selected.getFirstName());
        } else {
            System.out.println("we do not have any bid for this project.");
        }
    }

    public void addProject(Project project) throws RedundantProject {
        projectManager.add(project.getTitle(), project);
    }

    public void bid(Bid bid) throws InsufficentBudget, InsufficentSkill {
        bidManager.submit(bid);
    }

    public void bid(String userId, String projectId, int amount) throws ProjectNotFound, UserNotFound, InsufficentBudget, InsufficentSkill {
        Bid bid = new Bid(userManager.find(userId), projectManager.find(projectId), amount);
        bidManager.submit(bid);
    }

    public boolean  findBid(String userId, String projectId) {
        ArrayList<Bid> bids = bidManager.getRepository();
        for (Bid bid : bids) {
            if (bid.getUser().getId().equals(userId) && bid.getProject().getId().equals(projectId)) {
                return  true;
            }
        }
        return false;
    }

    public User getUser(String id) throws UserNotFound {
        return userManager.find(id);
    }

    public Project getSuitableProject(String userId, String id) throws ProjectNotFound, InsufficentSkill, UserNotFound {
        User user = userManager.find(userId);
        Project project = projectManager.find(id);
        if (haveSkills(user, project))
            return project;
        else throw new InsufficentSkill("You don't have nough skills to see this project!");

    }

    public ArrayList<Project> getSuitableProjects(String userId) throws UserNotFound {
        User user = userManager.find(userId);
        HashMap<String,Project> repo = projectManager.getRepository();
        ArrayList<Project> projects = new ArrayList<>();
        for(HashMap.Entry<String, Project> entry : repo.entrySet()) {
            Project project = entry.getValue();
            if(haveSkills(user, project))
                projects.add(project);
        }
        return projects;
    }

    public ArrayList<User> getUserList(String userId) {
        ArrayList<User> userList = new ArrayList<>(userManager.getUserList());
        for (int i = 0; i < userList.size(); i++)
            if (userList.get(i).getId().equals(userId)) {
                userList.remove(i);
                break;
            }
        return userList;
    }

    public void addSkillToUser(String userId, String skillName) throws UserNotFound {
        User user = userManager.find(userId);
        user.addSkill(skillName);
    }

    public void addSkills(ArrayList<Skill> skills) {
        skillManager.fill(skills);
    }

    public void addProjects(ArrayList<Project> projects) throws RedundantProject {
        projectManager.fill(projects);
    }

    public void deleteUserSkill(String userId, String skillName) throws UserNotFound {
        User user = userManager.find(userId);
        user.deleteSkill(skillName);
    }

    public ArrayList<Skill> getUserAbilities(String userId) throws UserNotFound {
        ArrayList<Skill> allSkills = skillManager.getAllSkills();
        User user = userManager.find(userId);
        HashMap<String, UserSkill> userSkills = user.getSkills();
        ArrayList<Skill> result = new ArrayList<>();
        for (Skill skill:allSkills)
            if (!userSkills.containsKey(skill.getName()))
                result.add(skill);
        return result;
    }

    public void endorse(String endorser, String endorsed, String skillName) throws UserNotFound {
        User user = userManager.find(endorsed);
        user.endorse(endorser, skillName);
    }

}
