package persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import zcchat.Controller;

public class Serializer {

    private static String fileName = "data.bin";

    public static Controller readFile() {

        Controller mainController = null;

        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName));
            mainController = (Controller) input.readObject();

            input.close();

        } catch (FileNotFoundException e) {
            System.out.println(String.format("Arquivo %s não encontrado...", fileName));

        } catch (IOException e) {
            System.err.println(String.format("Erro na leitura do arquivo %s!", fileName));
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mainController;
    }

    public static void writeFile(Controller mainController) {

        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName));

            output.writeObject(mainController);
            output.flush();

            output.close();

        } catch (IOException e) {
            System.err.println(String.format("Erro na gravação do arquivo %s!", fileName));
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
