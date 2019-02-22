package Server;

import Exception.*;
import JobOonJa.JobOonJa;
import Page.*;
import Project.ProjectListDTO;
import Skill.*;
import User.User;

import com.jsoniter.JsonIterator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Server {
    private static final JobOonJa jobOonJa = JobOonJa.getInstance();

    public void start() throws IOException, RedundantUser, RedundantProject {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        this.initialize();
        server.createContext("/", new reqHandler());
        server.setExecutor(null);
        server.start();
    }

    private HashMap<String,UserSkill> makeUserSkill(){
        HashMap<String,UserSkill> skills = new HashMap<>();
        skills.put("HTML",new UserSkill("HTML",5));
        skills.put("Javascript",new UserSkill("Javascript",4));
        skills.put("C++",new UserSkill("C++",2));
        skills.put("Java",new UserSkill("Java",3));
        return skills;
    }

    private User makeUser(){
        this.makeUserSkill();
        return new User("1","علی","شریف‌زاده","برنامه‌نویس وب", "../../../img/kami.jpg",
                this.makeUserSkill(),"روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت");
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
        jobOonJa.addSkills(skillList.getSkills());
    }

    private void getProjects() throws IOException, RedundantProject {
        HttpGet httpGet = new HttpGet("http://142.93.134.194:8000/joboonja/project");
        String total = this.extractGetData(httpGet);
        ProjectListDTO projectList = JsonIterator.deserialize("{\"projects\":" + total + "}", ProjectListDTO.class);
        jobOonJa.addProjects(projectList.getProjects());
    }

    private void initialize() throws RedundantUser, IOException, RedundantProject {
        User user = this.makeUser();
        jobOonJa.register(user);
        this.getSkills();
        this.getProjects();
    }

    class reqHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String userId = "1"; // Hard coded only for this phase -> get from http exchange
            StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
            String context = tokenizer.nextToken();
            Page page;
            try {
                switch (context) {
                    case "user":
                        page = new UserPage(tokenizer.nextToken());
                        break;
                    case "project":
                        if (tokenizer.hasMoreTokens())
                            page = new SingleProjectPage(userId, tokenizer.nextToken());
                        else
                            page = new ProjectsPage(userId);
                        break;
                    default:
                        throw new PageNotFound("Page not found!");
                }
                page.render(httpExchange);
            } catch (HandleException notFound) {
                notFound.printStackTrace();
                notFound.handle(httpExchange);
            }
        }
    }

}