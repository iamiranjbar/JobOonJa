package ir.ac.ut.models.JobOonJa;

import com.jsoniter.JsonIterator;
import com.jsoniter.fuzzy.StringFloatDecoder;

import ir.ac.ut.dataAccess.dataMapper.bid.BidMapper;
import ir.ac.ut.dataAccess.dataMapper.endorse.EndorseMapper;
import ir.ac.ut.dataAccess.dataMapper.project.ProjectMapper;
import ir.ac.ut.dataAccess.dataMapper.skill.SkillMapper;
import ir.ac.ut.dataAccess.dataMapper.user.UserMapper;
import ir.ac.ut.dataAccess.dataMapper.userSkill.UserSkillMapper;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static ir.ac.ut.models.Skill.SkillManager.haveSkills;

public class JobOonJa {

    private static JobOonJa ourInstance;

    static {
        try {
            ourInstance = new JobOonJa();
        } catch (RedundantUser | RedundantProject | IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private BidManager bidManager;
    private UserMapper userMapper;
    private UserSkillMapper userSkillMapper;
    private SkillMapper skillMapper;
    private EndorseMapper endorseMapper;
    private BidMapper bidMapper;
    private ProjectMapper projectMapper;
    

    public static JobOonJa getInstance() {
        return ourInstance;
    }

    private JobOonJa() throws RedundantUser, RedundantProject, IOException, SQLException {
        userMapper = UserMapper.getInstance();
        userSkillMapper = UserSkillMapper.getInstance();
        skillMapper = SkillMapper.getInstance();
        endorseMapper = EndorseMapper.getInstance();
        bidMapper = BidMapper.getInstance();
        projectMapper = ProjectMapper.getInstance();
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
        return new User("1","علی","شریف‌زاده","1","1","برنامه‌نویس وب", "http://aamooze.com/wp-content/uploads/2018/05/asghar.jpg",
                this.makeUserSkill(),"روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت");
    }

    private User makeUser(String id, String firstName, String lastName, String jobTitle, String bio){
        this.makeUserSkill();
        return new User(id,firstName,lastName,"1"+id,"1"+id,jobTitle, "http://aamooze.com/wp-content/uploads/2018/05/asghar.jpg",this.makeUserSkill(),bio);
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

    private void getSkills() throws IOException, SQLException {
        HttpGet httpGet = new HttpGet("http://142.93.134.194:8000/joboonja/skill");
        String total = this.extractGetData(httpGet);
        SkillListDTO skillList = JsonIterator.deserialize("{\"skills\":" + total + "}", SkillListDTO.class);
        this.addSkills(skillList.getSkills());
    }

    private void getProjects() throws IOException, RedundantProject, SQLException {
        HttpGet httpGet = new HttpGet("http://142.93.134.194:8000/joboonja/project");
        String total = this.extractGetData(httpGet);
        ProjectListDTO projectList = JsonIterator.deserialize("{\"projects\":" + total + "}", ProjectListDTO.class);
        this.addProjects(projectList.getProjects());
    }

    private void initialize() throws RedundantUser, IOException, RedundantProject, SQLException {
        User user = this.makeAdminUser();
        this.register(user);
        this.register(this.makeUser("4", "علی", "عدالت", "توسعه دهنده رابط کاربری",
                "مخلص همه سینگلا!"));
        this.register(this.makeUser("5", "امیر", "رنجبر", "متخصص SEO", "خرابتم ننه!"));
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

    public void register(User user) throws RedundantUser, SQLException {
        userMapper.insert(user);
    }

    public String findRegisterUserByUsername(String username, String password) throws Exception {
        User user = userMapper.searchByUsername(username);
        if (user.getPassword().equals(password)) {
            return user.getId();
        }
        throw new Exception("invalid username or password.");
    }

    public boolean usernameExist(String username) {
        try {
            User user = userMapper.searchByUsername(username);
            return true;
        } catch (Exception e) {
            return  false;
        }
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

    public void addProject(Project project) throws RedundantProject, SQLException {
        projectMapper.insert(project);
    }
    
    private void submit(Bid bid) throws SQLException, InsufficentSkill, InsufficentBudget {
    	 if (bid.getAmount() <= bid.getProject().getBudget()) {
             if (haveSkills(bid.getUser(),bid.getProject()))
                 bidMapper.insert(new BidDTO(bid.getUser().getId(), bid.getProject().getId(), bid.getAmount()));
             else
                 throw new InsufficentSkill("You don't have enough skills!");
         }
         else{
             throw new InsufficentBudget("Your needing budget is very high!");
         }
    }

    public void bid(Bid bid) throws InsufficentBudget, InsufficentSkill, SQLException {
        this.submit(bid);
    }

    public void bid(String userId, String projectId, int amount) throws ProjectNotFound, UserNotFound, InsufficentBudget, InsufficentSkill, SQLException {
        Bid bid = new Bid(userMapper.find(userId), projectMapper.find(projectId), amount);
        this.submit(bid);
    }

    public boolean findBid(String userId, String projectId) throws SQLException {
    	BidDTO bid = bidMapper.find(projectId, userId);
    	if (bid == null) {
    		return false;
    	}
    	return true;
    }

    public User getUser(String id) throws UserNotFound, SQLException {
        return userMapper.find(id);
    }

    public ArrayList<User> searchUser(String name) throws SQLException {
        return userMapper.searchByName(name);
    }

    public Project getSuitableProject(String userId, String id) throws ProjectNotFound, InsufficentSkill, UserNotFound, SQLException {
        User user = userMapper.find(userId);
        Project project = projectMapper.find(id);
        if (haveSkills(user, project))
            return project;
        else throw new InsufficentSkill("You don't have nough skills to see this project!");

    }

    public ArrayList<Project> getSuitableProjects(String userId, String limit) throws UserNotFound, SQLException {
        User user = userMapper.find(userId);
        ArrayList<Project> repo = (ArrayList<Project>) projectMapper.findAllSuitable(userId, limit);

//        ArrayList<Project> projects = new ArrayList<>();
//        for( Project entry : repo) {
//            if(haveSkills(user, entry))
//                projects.add(entry);
//        }
        return repo;
    }

    public ArrayList<Project> searchProjects(String searchField) throws SQLException {
        return projectMapper.search(searchField);
    }

    public ArrayList<User> getUserList(String userId) throws SQLException {
        ArrayList<User> userList = new ArrayList<>(userMapper.findAll());
        for (int i = 0; i < userList.size(); i++)
            if (userList.get(i).getId().equals(userId)) {
                userList.remove(i);
                break;
            }
        return userList;
    }

    public void addSkillToUser(String userId, String skillName) throws UserNotFound, SQLException {
       userSkillMapper.insert(new UserSkill(skillName, 0), userId);
    }

    private void addSkills(ArrayList<Skill> skills) throws SQLException {
        for(Skill skill : skills) {
        	skillMapper.insert(skill);
        }
    }

    private void addProjects(ArrayList<Project> projects) throws RedundantProject, SQLException {
        for(Project project : projects) {
        	projectMapper.insert(project);
        }
    }

    public void deleteUserSkill(String userId, String skillName) throws UserNotFound, SQLException {
        userSkillMapper.delete(new UserSkill(skillName, 0), userId);
    }

    public ArrayList<Skill> getUserAbilities(String userId) throws UserNotFound, SQLException {
        ArrayList<Skill> allSkills = (ArrayList<Skill>) skillMapper.findAll();
        User user = userMapper.find(userId);
        HashMap<String, UserSkill> userSkills = user.getSkills();
        ArrayList<Skill> result = new ArrayList<>();
        for (Skill skill:allSkills)
            if (!userSkills.containsKey(skill.getName()))
                result.add(skill);
        return result;
    }

    public void endorse(String endorser, String endorsed, String skillName) throws UserNotFound, SQLException {
        endorseMapper.insert(endorser, endorsed, skillName);
    }

}
