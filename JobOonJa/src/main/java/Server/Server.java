package Server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private HttpServer server;
    public void start() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(8080),0);
        this.createContexts();
    }

    private void createContexts(){
        this.server.createContext("project");
        this.server.createContext("project/{id}");
        this.server.createContext("user/{id}");
    }
}