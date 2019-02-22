package Page;

import JobOonJa.JobOonJa;
import Project.Project;
import Exception.*;

import htmlflow.HtmlView;
import htmlflow.StaticHtml;
import org.xmlet.htmlapifaster.Body;
import org.xmlet.htmlapifaster.Html;
import org.xmlet.htmlapifaster.Table;

import java.util.ArrayList;
import java.util.Map;

public class ProjectsPage extends Page{
    private String userId;

    @Override
    protected String renderPageContent() throws UserNotFound {
        ArrayList<Project> projects = JobOonJa.getInstance().getSuitableProjects(userId);
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
        for(Project project : projects) {
            table = table.tr()
                    .th().text(project.getId()).__()
                    .th().text(project.getTitle()).__()
                    .th().text(String.valueOf(project.getBudget())).__()
                    .__();
        }
        return table.__().__().__().render();
    }

    public ProjectsPage(String userId) {
        this.userId = userId;
    }
}
