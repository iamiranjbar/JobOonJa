package Page;

import Exception.*;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class Page {

    public void render(HttpExchange httpExchange) throws UserNotFound, IOException, ProjectNotFound, InsufficentSkill {
        String htmlFile = renderPageContent();
        sendHttpResponse(httpExchange, htmlFile);
    }

    abstract protected String renderPageContent() throws UserNotFound, ProjectNotFound, InsufficentSkill;

    private void sendHttpResponse(HttpExchange httpExchange, String htmlFile) throws IOException{
        byte[] bytes = htmlFile.getBytes(StandardCharsets.UTF_8);
        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
