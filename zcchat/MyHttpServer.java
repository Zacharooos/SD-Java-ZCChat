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
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "ISO-8859-1");
        BufferedReader br = new BufferedReader(isr);

        // Recuperando objeto de payload
        String payloadString = Payload.decodeBufferFromSocket(br);
        Payload payloadObj = Payload.deserializeHashMap(payloadString);
        br.close();

        // Montando resposta para cliente
        // TODO: Chamar alguma função do controller?
        Payload responsePayload = new Payload((String) payloadObj.get("func"), payloadObj.get("args"), "RESPOSTA");
        String response = Payload.serializeHashMap(responsePayload);

        try {
            Thread.sleep(5000); // Sleep por 5 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Responde com um código de status 200 (OK) e o corpo da requisição recebido
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=ISO-8859-1");
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(response.getBytes());
        }

        // Fechar conexão
        exchange.close();
    }
}
