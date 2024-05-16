package zcchat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Handles {
    // Classe que vai conter todas as subclasses de handles para as rotas

    // Handle de exemplo
    static class SampleHandler implements HttpHandler {
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
            Payload responsePayload = new Payload((String) payloadObj.get("func"), payloadObj.get("args"), "ADRIANOOOO");
            String response = Payload.serializeHashMap(responsePayload);

            // Responde com um código de status 200 (OK) e o corpo da requisição recebido
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=ISO-8859-1");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (DataOutputStream outputStream = new DataOutputStream(exchange.getResponseBody())) {
                System.out.println("aaa");
                outputStream.writeBytes(response);
                outputStream.flush();
            }
            System.out.println("bbb");
            exchange.close();
        }
    }

    // Handle de para login do cliente
    static class LoginHandler implements HttpHandler {
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
            Payload responsePayload = new Payload((String) payloadObj.get("func"), payloadObj.get("args"), "OK");
            String response = Payload.serializeHashMap(responsePayload);

            // Responde com um código de status 200 (OK) e o corpo da requisição recebido
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=ISO-8859-1");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (DataOutputStream outputStream = new DataOutputStream(exchange.getResponseBody())) {
                System.out.println("aaa");
                outputStream.writeBytes(response);
                outputStream.flush();
            }
            System.out.println("bbb");
            exchange.close();

            // Iniciando sistema de ping para o cliente
        }
    }

    // Handle para recepção de ping do cliente
    static class PingHandler implements HttpHandler {
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
            Payload responsePayload = new Payload((String) payloadObj.get("func"), payloadObj.get("args"), "OK");
            String response = Payload.serializeHashMap(responsePayload);

            // Responde com um código de status 200 (OK) e o corpo da requisição recebido
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=ISO-8859-1");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (DataOutputStream outputStream = new DataOutputStream(exchange.getResponseBody())) {
                System.out.println("aaa");
                outputStream.writeBytes(response);
                outputStream.flush();
            }
            System.out.println("bbb");
            exchange.close();

            // Iniciando sistema de ping para o cliente
        }
    }

    // Handle para criação de conta
    static class CreateAccountHandler implements HttpHandler {
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
            Payload responsePayload = new Payload((String) payloadObj.get("func"), payloadObj.get("args"), "OK");
            String response = Payload.serializeHashMap(responsePayload);

            // Responde com um código de status 200 (OK) e o corpo da requisição recebido
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=ISO-8859-1");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (DataOutputStream outputStream = new DataOutputStream(exchange.getResponseBody())) {
                System.out.println("aaa");
                outputStream.writeBytes(response);
                outputStream.flush();
            }
            System.out.println("bbb");
            exchange.close();

            // Iniciando sistema de ping para o cliente
        }
    }
}
