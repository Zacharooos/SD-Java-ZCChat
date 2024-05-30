package zcchat;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        // Iniciando controller
        Controller.load();

        // TODO: Fazer try catch?
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new Handles.SampleHandler());
        server.createContext("/createUser", new Handles.CreateAccountHandler());
        server.createContext("/deleteUser", new Handles.DeleteAccountHandler());
        server.createContext("/alterPassword", new Handles.AlterPasswordHandle());
        server.createContext("/login", new Handles.LoginHandler());
        server.createContext("/logout", new Handles.LogoutHandler());
        server.createContext("/ping", new Handles.PingHandler());
        server.createContext("/pingList", new Handles.ListPingHandler());
        server.createContext("/sendMessage", new Handles.SendMessageHandler());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();

        System.out.println("Server started on port " + port);
    }
}
