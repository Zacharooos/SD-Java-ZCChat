package zcchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class servidor {
    public static void main(String[] args) {
        int portNumber = 6792; // Porta na qual o servidor estará ouvindo

        //TODO: Handler de porta que o blockz vai fazer

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("S: Servidor TCP aguardando conexões na porta " + portNumber + "...");

            // Aguarda conexões dos clientes
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept(); // Aceita a conexão do cliente
                    System.out.println("S: Conexão estabelecida com o cliente " + clientSocket.getInetAddress());

                    // Cria um novo thread para lidar com o cliente
                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    clientHandler.start();
                } catch (IOException e) {
                    System.err.println("S: Erro ao aceitar a conexão do cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("S: Erro ao abrir o servidor: " + e.getMessage());
        }
    } 
}

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            // Recebe dados do cliente
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Montando objeto do payload recebido
            String requestString = Payload.decodeBufferFromSocket(in);
            Payload receivedPayload = Payload.deserializeHashMap(requestString);
            System.out.println("S: Mensagem recebida do cliente " + clientSocket.getInetAddress() + ": \n" + receivedPayload.get("func").toString());
            
            // Identificar solicitação
            String functionRequested = receivedPayload.get("func").toString();
            Object arguments = receivedPayload.get("args");
            Payload responsePayload = null;
            switch (functionRequested) {
                case "retrieveContactsOnline":
                    // TODO Tratar função com argumentos
                    // TODO: Chamar controller com a função solicitada
                    responsePayload = new Payload(functionRequested, arguments, "RESPOSTA");
                    break;

                case "bolinha":
                    // TODO Tratar função com argumentos
                    // TODO: Chamar controller com a função solicitada
                    break;
            
                default:
                    responsePayload = new Payload(functionRequested, arguments, "404 NOT FOUND");
                    break;
            }

            // TODO: Retornar par ao cliente com o resultado da solicitação

            // Montando string do payload da resposta
            String responseString = Payload.serializeHashMap(responsePayload);

            // Envia dados de volta para o cliente
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(responseString);

            // Fecha a conexão com o cliente
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("S: Erro de E/S com o cliente: " + e.getMessage());
        }
    }
}