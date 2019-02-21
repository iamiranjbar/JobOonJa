package Server;

import Page.*;
import Exception.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.StringTokenizer;

public class Server {
    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new reqHandler());

    }

    class reqHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
            String context = tokenizer.nextToken();
            Page page;
            try {
                switch (context) {
                    case "user":
                        page = new UserPage(tokenizer.nextToken());
                        break;
                    case "project":
                        if (tokenizer.hasMoreTokens())
                            page = new SingleProjectPage(tokenizer.nextToken());
                        else
                            page = new ProjectsPage();
                        break;
                    default:
                        throw new PageNotFound("Page not found!");
                }
                page.render(httpExchange);
            } catch (HandleException notFound) {
                notFound.printStackTrace();
                notFound.handle(httpExchange);
            }
        }
    }

}