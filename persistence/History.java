package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

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

    public static void writeHistory(Usuario cliente, String log) {
        createFile("historico");

        String filePath = "historico/" + cliente.get_username() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(log);
            writer.newLine();
            // System.out.println("Texto adicionado ao arquivo com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ReadHistory(Usuario cliente) {
        System.out.println(" < ============= Historico ============= >\n");
        try (BufferedReader br = new BufferedReader(new FileReader("historico/" + cliente.get_username() + ".txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n < ================ FIM ================ >\n");
    }

    public static void removeHistory(Usuario cliente) {

        String filePath = "historico/" + cliente.get_username() + ".txt";  

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
