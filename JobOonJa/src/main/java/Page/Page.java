package Page;

import Exception.*;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface Page {
    public void render(HttpExchange httpExchange) throws ProjectNotFound, UserNotFound, IOException;
}
