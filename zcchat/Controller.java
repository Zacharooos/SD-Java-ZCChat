package zcchat;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import persistence.Serializer;

public class Controller implements Serializable {
    private static Controller instance;

    private Map<String, Usuario> users;
    private Map<String, Usuario> onlineUsers;

    private Controller(){
        users = new TreeMap<>();
        onlineUsers = new TreeMap<>();
    }

    public void addUser(String username, String password){
        instance.users.put(username, new Usuario(username, password));
        Controller.save();
    }

    public String loginUser(String username, String password){
        Usuario user = instance.users.get(username);
        if (user == null) {
            return "USER NOT FOUND";
        }
        if (!user.validatePassword(password)){
            return "SENHA INCORRETA";
        }
        onlineUsers.put(username, null);
        return "OK";
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

    public static Controller getController(){
        return instance;
    }
}
