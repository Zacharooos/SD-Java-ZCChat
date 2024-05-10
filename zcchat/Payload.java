package zcchat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Payload extends HashMap<String, Object> {
    public Payload(String func, Object args) {
        super();
        this.put("func", func);
        this.put("args", args);
        this.put("response", "NULL");
    }

    public Payload(String func, Object args, Object response) {
        super();
        this.put("func", func);
        this.put("args", args);
        this.put("response", response);
    }

    public static String decodeBufferFromSocket(BufferedReader input){
        try {
            StringBuilder sb = new StringBuilder();
            int character;
            while ((character = input.read()) != -1) {
                sb.append((char) character);
            }
            String textoCompleto = sb.toString();
            return textoCompleto;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializeHashMap(Payload payload) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(payload);
            objectOutputStream.close();
            return byteArrayOutputStream.toString("ISO-8859-1");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Payload deserializeHashMap(String payloadString) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(payloadString.getBytes("ISO-8859-1"));
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Payload mapa = (Payload) objectInputStream.readObject();
                objectInputStream.close();
                return mapa;

            }catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
        }
    }

}
