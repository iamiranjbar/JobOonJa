package Page;

import Project.ProjectManager;
import com.sun.net.httpserver.HttpExchange;
import htmlflow.StaticHtml;
import Exception.*;

import java.io.IOException;
import java.io.OutputStream;

public class SingleProjectPage implements Page {

    String id;

    @Override
    public void render(HttpExchange httpExchange) throws ProjectNotFound, UserNotFound, IOException {
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
                .li().text("title: " + ProjectManager.getInstance().find(id).getTitle())
                .__()
                .li().text("description: " + ProjectManager.getInstance().find(id).getDescription())
                .__()
                .li().img().attrSrc(ProjectManager.getInstance().find(id).getImageURL()).attrStyle("width: 50px; height: 50px;")
                .__().text("imageUrl: ")
                .__()
                .li().text("budget: " + String.valueOf(ProjectManager.getInstance().find(id).getBudget()))
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
