package Page;

import JobOonJa.JobOonJa;
import htmlflow.StaticHtml;
import Exception.*;
import Project.*;
import org.xmlet.htmlapifaster.EnumDirType;

public class SingleProjectPage extends Page {
    private String id;
    private String userId;

    @Override
    protected String renderPageContent() throws ProjectNotFound, UserNotFound, InsufficentSkill {
        Project project = JobOonJa.getInstance().getSuitableProject(userId,id);
        return StaticHtml
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
                .li().text("title: ")
                .div().attrDir(EnumDirType.RTL).text(project.getTitle()).__()
                .__()
                .li().attrDir(EnumDirType.LTR).text("description: ")
                .div().attrDir(EnumDirType.RTL).text(project.getDescription()).__()
                .__()
                .li().text("imageUrl: ").img().attrSrc(project.getImageURL()).attrStyle("width: 50px; height: 50px;")
                .__()
                .__()
                .li().text("budget: " + String.valueOf(project.getBudget()))
                .__()
                .__()
                .__()
                .__() //body
                .__() //html
                .render();
    }

    public SingleProjectPage(String userId, String id) {
        this.userId = userId;
        this.id = id;
    }
}
