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

    public String addUser(String username, String password){
        // Verificando se user ja existe
        Usuario existente = instance.users.get(username);
        if (existente != null) {
            return "USERNAME JA EXISTENTE";
        }
        instance.users.put(username, new Usuario(username, password));
        Controller.save();
        return "OK";
    }

    public String deleteUser (String username){
        instance.users.remove(username, instance.users.get(username));
        Controller.save();
        return "OK";
    }

    public String alterPassword (String username, String newPassword){
        instance.users.replace(username, instance.users.get(username), new Usuario(username, newPassword));
        Controller.save();
        return "OK";
    }

    public String loginUser(String username, String password){
        Usuario user = instance.users.get(username);
        if (user == null) {
            return "USER NOT FOUND";
        }
        if (!user.validatePassword(password)){
            return "SENHA INCORRETA";
        }
        instance.onlineUsers.put(username, user);
        user.turnStatus(1);
        user.ping();
        return "OK";
    }

    public void logoutUser(String username){
        Usuario user = instance.onlineUsers.get(username);
        user.turnStatus(0);
        instance.onlineUsers.remove(username);
        return;
    }

    public void pingListener(String username){
        Usuario user = instance.onlineUsers.get(username);
        if(user == null){
            return;
        }

        // Criando thread de verificação
        class PingListener extends Thread{
            public void run(){
                while (true) {
                    try{
                        System.out.println("checking... " + username + "\n");
                        if (!user.checkLastPing()) {
                            System.out.println("User " + username + " desconectado\n");
                            instance.logoutUser(username);
                            return;
                        }
                        Thread.sleep(30000);
                    }catch(InterruptedException err){
                        System.out.println("Erro no PingListener do user " + username + "\n");
                    }
                }
            }
        }

        // Iniciando thread de verificação
        PingListener thread = new PingListener();
        thread.start();

        return;
    }

    public String pingUser(String username){
        Usuario user = instance.onlineUsers.get(username);
        if(user == null){
            return "SESSAO EXPIRADA";
        }
        user.ping();
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
