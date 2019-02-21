package Server;

import JobOonJa.JobOonJa;
import Page.*;
import Exception.*;
import Skill.*;
import User.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Server {
    private static final JobOonJa jobOonJa = JobOonJa.getInstance();
    private static final SkillManager skillManager = SkillManager.getInstance();
    public void start() throws IOException, RedundantUser {
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
        return new User("۱","علی","شریف‌زاده","برنامه‌نویس وب", "../../../img/kami.jpg",
                this.makeUserSkill(),"روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت");
    }

    private void getSkills() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://142.93.134.194:8000/joboonja/skill");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line, total = "";
        while ((line = reader.readLine()) != null) {
            total += line;
        }
        System.out.println(total);
    }

    private void initialize() throws RedundantUser, IOException {
        User user = this.makeUser();
        jobOonJa.register(user);
        this.getSkills();
    }

    class reqHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
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
                            page = new SingleProjectPage(tokenizer.nextToken());
                        else
                            page = new ProjectsPage();
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