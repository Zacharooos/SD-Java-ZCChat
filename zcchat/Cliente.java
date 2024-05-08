package zcchat;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Cliente {
    private static Socket socket;

	public static void cliente_menu() {
		Scanner scanner = new Scanner(System.in);
        int menu_index = -1;

        while(menu_index != 0){
            System.out.println("Oque você deseja fazer?\n * 1 - Procurar contatos online.\n * 2 - Falar com um contato online.\n * 3 - Visualizar histórico.\n * 4 - Mensagem para contatos offline\n * 5 - Alterar credênciais");

            menu_index = scanner.nextInt();

            switch (menu_index) {
                case 1:
                    System.out.println("Procurando contatos...");
                    cliente_retrive_contacts_online();
                    break;

                case 2:
                    // Precisa de outro switch para perguntar qual o contato da comunicação.
                    System.out.println("Verificando se o contato está online...");
                    cliente_message_contacts_online()
                    break;

                case 3:
                    // Precisa enviar o nome do cliente que está estabelecendo o contato
                    System.out.println("Buscando histórico...");
                    cliente_retrive_history()
                    break;

                case 4:
                    // Precisa de outro switch para perguntar qual o contato da comunicação.
                    System.out.println("Enviado mensagem offline...");
                    cliente_message_contacts_offline()
                    break;

                case 5:
                    System.out.println("Acessando credênciais...");
                    cliente_contact_info()
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
            PrintWriter out = new PrintWriter(Cliente.socket.getOutputStream(), true);
            out.println("retrieve_contacts");

            BufferedReader in = new BufferedReader(new InputStreamReader(Cliente.socket.getInputStream()));
            System.out.println(in.readLine());
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.to_string());
        }
    }

    public static void cliente_message_contacts_online() {
        try {
            PrintWriter out = new PrintWriter(Cliente.socket.getOutputStream(), true);
            out.println("message_contacts_on");
    
            BufferedReader in = new BufferedReader(new InputStreamReader(Cliente.socket.getInputStream()));
            System.out.println(in.readLine());
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.to_string());
        }
    }

    public static void cliente_retrive_history() {
        try {
            PrintWriter out = new PrintWriter(Cliente.socket.getOutputStream(), true);
            out.println("retrive_history");
    
            BufferedReader in = new BufferedReader(new InputStreamReader(Cliente.socket.getInputStream()));
            System.out.println(in.readLine());
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.to_string());
        }
    }

    public static void cliente_message_contacts_offline() {
        try {
            PrintWriter out = new PrintWriter(Cliente.socket.getOutputStream(), true);
            out.println("message_contacts_off");
    
            BufferedReader in = new BufferedReader(new InputStreamReader(Cliente.socket.getInputStream()));
            System.out.println(in.readLine());
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.to_string());
        }
    }

    public static void cliente_contact_info() {
        try {
            PrintWriter out = new PrintWriter(Cliente.socket.getOutputStream(), true);
            out.println("contact_info");
    
            BufferedReader in = new BufferedReader(new InputStreamReader(Cliente.socket.getInputStream()));
            System.out.println(in.readLine());
        
        } catch (Exception e) {
            System.out.println("Erro na comunicação: " + e.to_string());
        }
    }

    public static void main(String[] args) {
        String serverHostname = "127.0.0.1"; // Altere para o endereço IP ou nome do servidor

        try {
            // Realiza conexão com o servidor.
            System.out.println("C: Conectando ao servidor " + serverHostname + " na porta 6788...");
            Cliente.socket = new Socket(serverHostname, 6792);
            System.out.println("C: Conectado ao servidor.");

            // Chama a função menu.
            Cliente.cliente_menu();

            // Envia dados para o servidor
            PrintWriter out = new PrintWriter(Cliente.socket.getOutputStream(), true);
            out.println("C: Olá, servidor!");

            // Recebe dados do servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(Cliente.socket.getInputStream()));
            System.out.println("C: Mensagem recebida do servidor: " + in.readLine());

            // Fecha a conexão
            Cliente.socket.close();
        } catch (UnknownHostException e) {
            System.err.println("C: Endereço do servidor não encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("C: Erro de E/S: " + e.getMessage());
        }
    }
}
