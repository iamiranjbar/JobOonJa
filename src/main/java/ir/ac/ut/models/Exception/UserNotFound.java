package ir.ac.ut.models.Exception;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class UserNotFound extends HandleException {
    public UserNotFound(String message) {
        super(message);
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response =
                "<html>"
                        + "<body>User not found.</body>"
                        + "</html>";
        httpExchange.sendResponseHeaders(404, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
