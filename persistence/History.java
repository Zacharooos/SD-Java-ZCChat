package persistence;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

import zcchat.Mensagem;
import zcchat.Usuario;

public class History {
    public static void createFile(String directoryPath) {

        File directory = new File(directoryPath);

        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Diretório criado com sucesso!");
            } else {
                System.out.println("Falha ao criar o diretório.");
            }
        } else {
            System.out.println("O diretório já existe.");
        }
    }

    public static void writeHistory(Mensagem mensagem, String log) {
        createFile("historico");

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("historico/" + mensagem.get_emissor().get_username() + ".bin", true))) {
            dos.writeUTF(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ReadHistory(Usuario cliente) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream("historico/" + cliente.get_username() + ".bin"))) {
            
            System.out.println(" < ============= Historico ============= >\n");
            while (true) {
                try {
                    String str = dis.readUTF();
                    System.out.println(str);
                
                } catch (Exception e) {
                    System.out.println("\n < ================ FIM ================ >\n");
                    break; 
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao recuperar historico: Nao existe");
        }
    }

    public static void removeHistory(Usuario cliente) {

        String filePath = "historico/" + cliente.get_username() + ".bin";  

        File file = new File(filePath);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Arquivo apagado com sucesso!");

            } else {
                System.out.println("Falha ao apagar o arquivo.");

            }
        } else {
            System.out.println("O arquivo não existe.");
        }
    }

}
