package Page;

import JobOonJa.JobOonJa;
import User.User;
import htmlflow.StaticHtml;
import Exception.*;

import com.sun.net.httpserver.HttpExchange;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.OutputStream;

public class UserPage extends Page {
    private String id;

    @Override
    protected String renderPageContent() throws UserNotFound {
        User user = JobOonJa.getInstance().getUser(id);
        return StaticHtml
                .view()
                .html().attrLang("en")
                .head()
                .meta().attrCharset("UTF-8").__()
                .title().text("User").__()
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
    }

    public UserPage(String id) {
        this.id = id;
    }
}
