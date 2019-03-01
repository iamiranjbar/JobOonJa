package models.Exception;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class InsufficentSkill extends HandleException {

    public InsufficentSkill(String message) {
        super(message);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response =
                "<html>"
                        + "<body>You don't have enough skills to see this project!</body>"
                        + "</html>";
        httpExchange.sendResponseHeaders(403, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
