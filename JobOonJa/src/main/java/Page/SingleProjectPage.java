package Page;

import JobOonJa.JobOonJa;
import Project.ProjectManager;
import com.sun.net.httpserver.HttpExchange;
import htmlflow.StaticHtml;
import Exception.*;
import Project.*;

import java.io.IOException;
import java.io.OutputStream;

public class SingleProjectPage implements Page {

    String id;

    @Override
    public void render(HttpExchange httpExchange) throws ProjectNotFound, UserNotFound, IOException {
        Project project = JobOonJa.getInstance().getProject(id);
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
                .li().text("title: " + project.getTitle())
                .__()
                .li().text("description: " + project.getDescription())
                .__()
                .li().img().attrSrc(project.getImageURL()).attrStyle("width: 50px; height: 50px;")
                .__().text("imageUrl: ")
                .__()
                .li().text("budget: " + String.valueOf(project.getBudget()))
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

    public SingleProjectPage(String id) {
        this.id = id;
    }
}
