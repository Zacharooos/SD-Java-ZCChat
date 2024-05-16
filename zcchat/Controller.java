package zcchat;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import persistence.Serializer;

public class Controller implements Serializable {
    private static Controller instance;

    Map<String, Usuario> users;
    Map<String, Usuario> onlineUsers;

    private Controller(){
        users = new TreeMap<>();
        onlineUsers = new TreeMap<>();
    }

    public void addUser(String username, String password){
        users.put(username, new Usuario(username, password));
        Controller.save();
    }

    // Funções de persistence
    public static void load(){
        instance = Serializer.readFile();
        if(instance == null){
            instance = new Controller(); 
        }
    }

    public static void save() {
        Serializer.writeFile(instance);
    }
}
