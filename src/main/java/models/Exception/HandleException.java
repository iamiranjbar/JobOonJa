package models.Exception;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public abstract class HandleException extends Exception{
    public HandleException(String message){
        super(message);
    }

    abstract public void handle(HttpExchange httpExchange) throws IOException;
}