package zcchat;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.net.URL;

public class MyHttpClient {

	public static void client_singup(Scanner scanner) {
        String login;
        String password;
        while(true){
        
        System.out.println("> ==== > SingUp ZCChat < ==== <\n");

            System.out.println(" > Novo Login: ");
            login = scanner.nextLine();

            System.out.println(" > Nova Senha: ");
            password = scanner.nextLine();

            // Debugging Apagar
            System.out.println(login);    
            System.out.println(password);
            // Debugging Apagar

            try {
                // Montando corpo da requisição
                Payload loginPaylodObj = new Payload("newUser");
                loginPaylodObj.put("username", login);
                loginPaylodObj.put("password", password);
                
                // Enviando Payload e recebendo resposta
                Payload responsePayloadObj = HttpConnect(loginPaylodObj, "createUser");
    
                if (responsePayloadObj.get("response").equals("OK")){
                    System.out.println("CADASTRO EFETUADO!! ... \n -> Redirecionando para LOGIN");
                    break;
                }
                System.out.println(responsePayloadObj.get("response").toString());


            } catch (Exception e) {
                System.out.println("Erro na comunicação: " + e.toString());
            }

        }
	}

	public static void client_logon(Scanner scanner) {
        String login;
        String password;
        while(true){
        
        System.out.println("> ==== > Logon ZCChat < ==== <\n");

            System.out.println(" > Login: ");
            login = scanner.nextLine();

            System.out.println(" > Senha: ");
            password = scanner.nextLine();

            // Debugging Apagar
            System.out.println(login);    
            System.out.println(password);
            // Debugging Apagar

            try {
                // Montando corpo da requisição
                Payload loginPaylodObj = new Payload("newUser");
                loginPaylodObj.put("username", login);
                loginPaylodObj.put("password", password);
                
                // Enviando Payload e recebendo resposta
                Payload responsePayloadObj = HttpConnect(loginPaylodObj, "login");
    
                if (responsePayloadObj.get("response").equals("OK")){
                    System.out.println("LOGIN EFETUADO!!");
                    break;
                }
                System.out.println(responsePayloadObj.get("response").toString());

                // TODO: Guardar o usuário que está logado!

            } catch (Exception e) {
                System.out.println("Erro na comunicação: " + e.toString());
            }

        }
	}
    
	public static void client_menu(Scanner scanner) {
        int menu_index = -1;
        String input1;
        String input2;

        while(menu_index != 0){
            System.out.println("Oque você deseja fazer?\n * 1 - Procurar contatos online.\n * 2 - Falar com um contato online.\n * 3 - Visualizar historico.\n * 4 - Mensagem broadcast\n * 5 - Gerenciar conta\n Outra tecla para sair");

            menu_index = scanner.nextInt();

            switch (menu_index) {
                case 1:
                    Utils.ClearConsole();
                    System.out.println("Procurando contatos...");
                    ClienteRetrieveContactsOnline();
                    // Utils.DisplayList();
                    // Exibir lista de contatos Online

                    break;

                case 2:
                    // Precisa de outro switch para perguntar qual o contato da comunicação.
                    Utils.ClearConsole();
                    
                    System.out.println("Informe o nome do contato:");
                    input1 = scanner.nextLine();

                    System.out.println("Informe a sua mensagem:");
                    input2 = scanner.nextLine();

                    System.out.println("Verificando se o contato está online...");
                    ClienteMessageContactOnline(input1, input2);
                    
                    break;

                case 3:
                    Utils.ClearConsole();
                    System.out.println("Buscando historico...");
                    ClienteRetrieveHistory();
                    break;

                case 4:
                    // Precisa de outro switch para perguntar qual o contato da comunicação.
                    Utils.ClearConsole();
                    System.out.println("Enviado mensagem broadcast");
                    ClienteMessageAllContacts();
                    break;

                case 5:
                    Utils.ClearConsole();
                    System.out.println("Acessando credenciais...");
                    ClienteInfo();
                    break;

                default:
                    System.out.println("Saindo");
                    menu_index = 0;
                    break;
            }
        }
	}
	
    public static void ClienteRetrieveContactsOnline() {
        try {
            // Montando corpo da requisição
            Payload objPayload = new Payload("retrieveContactsOnline", "Teste");
            
            // Enviando Payload e recebendo resposta
            Payload responsePayloadObj = HttpConnect(objPayload, "");

            System.out.println(responsePayloadObj.get("response"));
            
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.toString());
        }
    }

    public static void ClienteMessageContactOnline(String nome, String msg) {
        try {
            // Montando corpo da requisição, passando nome e mensagem
            Payload objPayload = new Payload("cliente.nome"); // TODO: Montar peyload certa
            
            // Enviando Payload e recebendo resposta
            Payload responsePayloadObj = HttpConnect(objPayload, "");

            String response = responsePayloadObj.get("response").toString();
            System.out.println(response);
            
            if (response == "UserNotFound"){
                System.out.println("O usuario nao esta online no momento!");
            
            } else if (response == "ServerError"){
                System.out.println("O houve algum erro durante o processamento, tente novamente!");
            } else {
                System.out.println("Eu -> " + nome + ": " + msg);
            }

        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.toString());
        }
    }

    public static void ClienteRetrieveHistory() {
        try {
            // Montando corpo da requisição
            Payload objPayload = new Payload("retrieveHistory", "Teste");
            
            // Enviando Payload e recebendo resposta
            Payload responsePayloadObj = HttpConnect(objPayload, "");

            System.out.println(responsePayloadObj.get("response"));
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.toString());
        }
    }

    public static void ClienteMessageAllContacts() {
        try {
            // Montando corpo da requisição
            Payload objPayload = new Payload("messageAllContacts", "Teste");
            
            // Enviando Payload e recebendo resposta
            Payload responsePayloadObj = HttpConnect(objPayload, "");

            System.out.println(responsePayloadObj.get("response"));
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.toString());
        }
    }

    public static void ClienteInfo() {
        try {
            // Montando corpo da requisição
            Payload objPayload = new Payload("clienteInfo", "Teste");
            
            // Enviando Payload e recebendo resposta
            Payload responsePayloadObj = HttpConnect(objPayload, "");

            System.out.println(responsePayloadObj.get("response"));
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.toString());
        }
    }

    @SuppressWarnings("deprecation")
    public static Payload HttpConnect(Payload requestPayloadObj, String route) throws IOException{
        // Configurando conexão
        String url = "http://localhost:8000/" + route;
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
            // int responseCode = connection.getResponseCode();

            String responseString = Payload.decodeBufferFromSocket(br);
            Payload responsePayloadObj = Payload.deserializeHashMap(responseString);
         
            // Imprimir o corpo da resposta
            connection.disconnect();
            return responsePayloadObj;
        }

    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("> ==== > ZCChat < ==== <\n\nSeja bem-vindo, selecione uma opcao:\n 1 - Realizar login\n 2 - Criar login\nOutro - Sair");
        int input = scanner.nextInt();

        if (input > 2 && input < 1) {
            System.out.println("Saindo...");
            scanner.close();
        } else if (input == 2){
            client_singup(scanner);
        }

        client_logon(scanner);

        // Inicializa Login
        client_menu(scanner);

        scanner.close();

    }
}
