package models.Exception;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class ProjectNotFound extends HandleException {
    public ProjectNotFound(String message) {
        super(message);
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response =
                "<html>"
                        + "<body>Project not found.</body>"
                        + "</html>";
        httpExchange.sendResponseHeaders(404, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
