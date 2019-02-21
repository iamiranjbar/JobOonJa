package Page;

import JobOonJa.JobOonJa;
import User.User;
import User.UserManager;
import htmlflow.StaticHtml;
import Exception.*;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class UserPage implements Page {

    private String id;

    @Override
    public void render(HttpExchange httpExchange) throws UserNotFound, IOException {
        User user = JobOonJa.getInstance().getUser(id);
        String htmlFile = StaticHtml
                .view()
                .html().attrLang("en")
                .head()
                .title().text("User").__()
                .meta().attrCharset("UTF-8").__()
                .__() //head
                .body()
                .div().attrClass("container")
                .ul()
                .li().text("id: " + id)
                .__()
                .li().text("first name: " + user.getFirstName())
                .__()
                .li().text("last name: " + user.getLastName())
                .__()
                .li().text("jobTitle: " + user.getJobTitle())
                .__()
                .li().text("bio: " + user.getBio())
                .__()
                .__()
                .__()
                .__() //body
                .__() //html
                .render();
        httpExchange.sendResponseHeaders(200, htmlFile.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(htmlFile.getBytes());
        os.close();
    }

    public UserPage(String id) {
        this.id = id;
    }
}
