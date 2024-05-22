package zcchat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Handles {
    // Classe que vai conter todas as subclasses de handles para as rotas

    static private Payload recieveRequest(HttpExchange exchange) throws IOException{
        // Recebe o corpo da requisição
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "ISO-8859-1");
        BufferedReader br = new BufferedReader(isr);

        // Recuperando objeto de payload
        String payloadString = Payload.decodeBufferFromSocket(br);
        Payload payloadObj = Payload.deserializeHashMap(payloadString);
        br.close();

        return payloadObj;
    }

    static private void sendResponse(HttpExchange exchange, Payload responsePayload) throws IOException{
        String response = Payload.serializeHashMap(responsePayload);

        // Responde com um código de status 200 (OK) e o corpo da requisição recebido
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=ISO-8859-1");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (DataOutputStream outputStream = new DataOutputStream(exchange.getResponseBody())) {
            System.out.println("S: Processado");
            outputStream.writeBytes(response);
            outputStream.flush();
        }
        System.out.println("S: Falha");
        exchange.close();
    }

    // Handle de exemplo
    static class SampleHandler implements HttpHandler {
    @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Recebe o corpo da requisição
            Payload payloadObj = recieveRequest(exchange);

            // Debugging, apagar:
            System.out.println(payloadObj.get("author"));

            // Montando resposta para cliente
            Payload responsePayload = new Payload("SERVER", "SUCCESS");

            // Responde com um código de status 200 (OK) e o corpo da requisição recebido
            sendResponse(exchange, responsePayload);
        }
    }

    // Handle para criação de conta
    static class CreateAccountHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Recebe o corpo da requisição
            Payload payloadObj = recieveRequest(exchange);

            // Obtendo valores do payload
            String username = (String) payloadObj.get("username");
            String password = (String) payloadObj.get("password");

            // Salvando no controller
            Controller controller = Controller.getController();
            String ret = controller.addUser(username, password);

            // Montando resposta para cliente
            Payload responsePayload = new Payload("SERVER", ret);

            // Responde com um código de status 200 (OK) e o corpo da requisição recebido
            sendResponse(exchange, responsePayload);
        }
    }

    // Handle para login de usuario
    static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Recebe o corpo da requisição
            Payload payloadObj = recieveRequest(exchange);

            // Obtendo valores do payload
            String username = (String) payloadObj.get("username");
            String password = (String) payloadObj.get("password");

            // Salvando no controller
            Controller controller = Controller.getController();
            String ret = controller.loginUser(username, password);
            
            // Montando resposta para cliente
            Payload responsePayload = new Payload("SERVER", ret);

            // Responde com um código de status 200 (OK) e o corpo da requisição recebido
            sendResponse(exchange, responsePayload);

            // Iniciando loop que ouvirá pings desse cliente
            try{
                controller.pingListener(username);
            }catch(InterruptedException err){
                System.out.println("Erro no PingListener do user " + username + "\n");

            }
        }
    }

    // Handle para ping de usuario
    static class PingHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Recebe o corpo da requisição
            Payload payloadObj = recieveRequest(exchange);

            // Obtendo valores do payload
            String username = (String) payloadObj.get("username");

            // Fazemdp ping pelo controller
            Controller controller = Controller.getController();
            String ret = controller.pingUser(username);

            // Montando resposta para cliente
            Payload responsePayload = new Payload("SERVER", ret);

            // Responde com um código de status 200 (OK) e o corpo da requisição recebido
            sendResponse(exchange, responsePayload);
        }
    }

    // Handle para logout de usuario
    static class LogoutHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Recebe o corpo da requisição
            Payload payloadObj = recieveRequest(exchange);

            // Obtendo valores do payload
            String username = (String) payloadObj.get("username");

            // Fazemdp ping pelo controller
            Controller controller = Controller.getController();
            controller.logoutUser(username);

            // Montando resposta para cliente
            Payload responsePayload = new Payload("SERVER", "OK");

            // Responde com um código de status 200 (OK) e o corpo da requisição recebido
            sendResponse(exchange, responsePayload);
        }
    }
}
