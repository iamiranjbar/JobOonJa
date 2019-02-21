package Page;

import JobOonJa.JobOonJa;
import Project.Project;
import Project.ProjectManager;
import com.sun.net.httpserver.HttpExchange;
import htmlflow.HtmlView;
import htmlflow.StaticHtml;
import org.xmlet.htmlapifaster.Body;
import org.xmlet.htmlapifaster.Html;
import org.xmlet.htmlapifaster.Table;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ProjectsPage implements Page{

    @Override
    public void render(HttpExchange httpExchange) throws IOException {
        Map<String, Project> projects = JobOonJa.getInstance().getProjects();
        Table<Body<Html<HtmlView>>> table = StaticHtml.view()
                .html().attrLang("en")
                .head().meta().attrCharset("UTF-8").__()
                .title().text("Projects").__()
                .style()
                .text("table {\n" +
                        "            text-align: center;\n" +
                        "            margin: 0 auto;\n" +
                        "        }\n" +
                        "        td {\n" +
                        "            min-width: 300px;\n" +
                        "            margin: 5px 5px 5px 5px;\n" +
                        "            text-align: center;\n" +
                        "        }")
                .__()
                .__()
                .body()
                .table().tr()
                .th().text("id").__()
                .th().text("title").__()
                .th().text("budget").__()
                .__();
        for(Project project : projects.values()) {
            table = table.tr()
                    .th().text(project.getId()).__()
                    .th().text(project.getTitle()).__()
                    .th().text(String.valueOf(project.getBudget())).__()
                    .__();
        }
        String htmlFile = table.__().__().__().render();
        httpExchange.sendResponseHeaders(200, htmlFile.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(htmlFile.getBytes());
        os.close();
    }
}
