package zcchat;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.net.URL;

public class MyHttpClient {

	public static void client_menu() {
		Scanner scanner = new Scanner(System.in);
        int menu_index = -1;

        while(menu_index != 0){
            System.out.println("Oque você deseja fazer?\n * 1 - Procurar contatos online.\n * 2 - Falar com um contato online.\n * 3 - Visualizar historico.\n * 4 - Mensagem broadcast\n * 5 - Gerenciar conta\n Outra tecla para sair");

            menu_index = scanner.nextInt();

            switch (menu_index) {
                case 1:
                    System.out.println("Procurando contatos...");
                    cliente_retrive_contacts_online();
                    break;

                case 2:
                    // Precisa de outro switch para perguntar qual o contato da comunicação.
                    System.out.println("Verificando se o contato está online...");
                    cliente_message_contacts_online();
                    break;

                case 3:
                    // Precisa enviar o nome do cliente que está estabelecendo o contato
                    System.out.println("Buscando historico...");
                    cliente_retrive_history();
                    break;

                case 4:
                    // Precisa de outro switch para perguntar qual o contato da comunicação.
                    System.out.println("Enviado mensagem offline...");
                    cliente_message_all_contacts();
                    break;

                case 5:
                    System.out.println("Acessando credenciais...");
                    cliente_contact_info();
                    break;

                default:
                    System.out.println("Saindo");
                    menu_index = 0;
                    break;
            }
        }

            scanner.close();
	}
	
    public static void cliente_retrive_contacts_online() {
        try {
            // Montando corpo da requisição
            Payload objPayload = new Payload("retrieveContactsOnline", "Teste");
            
            // Enviando Payload e recebendo resposta
            Payload responsePayloadObj = HttpConnect(objPayload);

            System.out.println(responsePayloadObj.get("response"));
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.toString());
        }
    }

    public static void cliente_message_contacts_online() {
        try {
            // Montando corpo da requisição
            Payload objPayload = new Payload("cliente_message_contacts_online", "Teste");
            
            // Enviando Payload e recebendo resposta
            Payload responsePayloadObj = HttpConnect(objPayload);

            System.out.println(responsePayloadObj.get("response"));
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.toString());
        }
    }

    public static void cliente_retrive_history() {
        try {
            // Montando corpo da requisição
            Payload objPayload = new Payload("cliente_retrive_history", "Teste");
            
            // Enviando Payload e recebendo resposta
            Payload responsePayloadObj = HttpConnect(objPayload);

            System.out.println(responsePayloadObj.get("response"));
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.toString());
        }
    }

    public static void cliente_message_all_contacts() {
        try {
            // Montando corpo da requisição
            Payload objPayload = new Payload("cliente_message_all_contacts", "Teste");
            
            // Enviando Payload e recebendo resposta
            Payload responsePayloadObj = HttpConnect(objPayload);

            System.out.println(responsePayloadObj.get("response"));
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.toString());
        }
    }

    public static void cliente_contact_info() {
        try {
            // Montando corpo da requisição
            Payload objPayload = new Payload("cliente_contact_info", "Teste");
            
            // Enviando Payload e recebendo resposta
            Payload responsePayloadObj = HttpConnect(objPayload);

            System.out.println(responsePayloadObj.get("response"));
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.toString());
        }
    }

    @SuppressWarnings("deprecation")
    public static Payload HttpConnect(Payload requestPayloadObj) throws IOException{
        // Configurando conexão
        String url = "http://localhost:8000/";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "text/plain; charset=ISO-8859-1");
        connection.setDoOutput(true);

        String requestBody = Payload.serializeHashMap(requestPayloadObj);

        // Enviando para servidor
        // Escreve o corpo da requisição no fluxo de saída
        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            outputStream.writeBytes(requestBody);
            outputStream.flush();
        }

        // Obtém o body da resposta do servidor
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),
        "ISO-8859-1"))) {
            // Obtém o código de resposta HTTP
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            String responseString = Payload.decodeBufferFromSocket(br);
            Payload responsePayloadObj = Payload.deserializeHashMap(responseString);
         
            // Imprimir o corpo da resposta
            System.err.println(responsePayloadObj.get("response"));
            connection.disconnect();
            return responsePayloadObj;
        }

    }

    public static void main(String[] args) throws IOException {

        // TODO: Login

        // Inicializa Login
        client_menu();


    }
}
