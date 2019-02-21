package Page;

import User.UserManager;
import htmlflow.StaticHtml;
import Exception.*;

public class UserPage implements Page {

    private String id;

    @Override
    public void render() throws UserNotFound {
        String htmlFile = StaticHtml
                .view()
                .html()
                .head()
                .title().text("User").__()
                .meta().attrCharset("UTF-8").__()
                .__() //head
                .body()
                .div().attrClass("container")
                .ul()
                .li().text("id: " + id)
                .__()
                .li().text("first name: " + UserManager.getInstance().find(id).getFirstName())
                .__()
                .li().text("last name: " + UserManager.getInstance().find(id).getLastName())
                .__()
                .li().text("jobTitle: " + UserManager.getInstance().find(id).getJobTitle())
                .__()
                .li().text("bio: " + UserManager.getInstance().find(id).getBio())
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
