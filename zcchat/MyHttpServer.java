package zcchat;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.OutputStream;

public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        // TODO: Fazer try catch?
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server started on port " + port);
    }
}



class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Recebe o corpo da requisição
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
        BufferedReader br = new BufferedReader(isr);

        // Recuperando objeto de payload
        String payloadString = Payload.decodeBufferFromSocket(br);
        Payload payloadObj = Payload.deserializeHashMap(payloadString);

        // StringBuilder requestBody = new StringBuilder();
        // String line;
        // while ((line = br.readLine()) != null) {
        //     requestBody.append(line);
        // }
        br.close();

        // Responde com um código de status 200 (OK) e o corpo da requisição recebido
        String response = "Recebido: " + payloadObj.get("func");
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
