package zcchat;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import persistence.Serializer;
import com.sun.net.httpserver.HttpExchange;

public class Controller implements Serializable {
    private static Controller instance;

    private Map<String, Usuario> users; // Lista de usuarios cadastrados
    private Map<String, Usuario> onlineUsers; // Lista de usuarios atualmente online
    private Map<String, List<Mensagem>> messageQueue; // Lista de mensagens a serem enviadas (para cada username)

    private Controller(){
        users = new TreeMap<>();
        onlineUsers = new TreeMap<>();
        messageQueue = new TreeMap<>();
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
        // Pegando usuario informado
        Usuario user = instance.users.get(username);

        // Alterando senha utilizando metodo do user
        user.changePassword(newPassword);

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

        // Verificando se usuario ja esta online
        if(instance.onlineUsers.get(username) != null){
            return "USUARIO JA LOGADO";
        }
        instance.onlineUsers.put(username, user);
        user.turnStatus(1);
        user.ping();
        return "OK";
    }

    public void logoutUser(String username){
        Usuario user = instance.onlineUsers.get(username);
        if(user == null){
            return;
        }
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
                        if (instance.onlineUsers.get(username) == null || !user.checkLastPing()) {
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
    }

    public void pingUser(String username, HttpExchange exchange) throws IOException{
        Payload response;
        synchronized (instance.onlineUsers) {
            Usuario user = instance.onlineUsers.get(username);
            if (user == null) {
                response = new Payload("SERVER", "SESSAO EXPIRADA");
                    Handles.sendResponse(exchange, response);
                return;
            }
            user.ping();
        }

        // Recuperando lista de mensagens do usuario
        List<Mensagem> userMessagesList = instance.messageQueue.get(username);
        if (userMessagesList == null) {
            userMessagesList = new ArrayList<Mensagem>();
            instance.messageQueue.put(username, userMessagesList);
        }
        synchronized (userMessagesList) {
            // Aguardando por uma mensagem ou timeout
            try {
                userMessagesList.wait(30000);
                // Thread.sleep(15000);

            } catch (InterruptedException err) {
                err.printStackTrace();
                return;
            }

            
            // Verificando se tem mensagem nova e montando payload
            if (userMessagesList.isEmpty()) {
                response = new Payload("SERVER", "OK");
            } else {
                response = new Payload("SERVER", "NEW MESSAGE");
                Mensagem message = userMessagesList.remove(0);
                response.put("message", message);
            }
            
            // Enviando response
            Handles.sendResponse(exchange, response);
        }
    }
    
    public String pingList(String username){
        List<String> temp = new ArrayList<>(instance.onlineUsers.keySet());
        temp.remove(username);
        return temp.toString();
    }

    // Funções de mensagens
    public Payload sendMessage(String author, String recipient, String messageText){
        // Declarando payload de resposta
        Payload ret = new Payload("SERVER");

        // Verifiando se usuarios se encontram online

        System.out.println("Recipient ->" + recipient + "\n");
        Usuario recipientObj = instance.onlineUsers.get(recipient);
        Usuario authorObj = instance.onlineUsers.get(author);
        if(recipientObj == null){
            ret.put("response", "DESTINATARIO OFFLINE");
            return ret;
        }
        if (authorObj == null) {
            ret.put("response", "AUTOR OFFLINE");
            return ret;
        }
        ret.put("response", "OK");

        // Criando mensagem e colocando na fila
        System.out.println(messageText);
        Mensagem message = new Mensagem(messageText, authorObj, recipientObj);
        List<Mensagem> userMessagesList = instance.messageQueue.get(recipient);
        if(userMessagesList == null){
            userMessagesList = new ArrayList<Mensagem>();
            instance.messageQueue.put(recipient, userMessagesList);
        }
        synchronized(userMessagesList){
            userMessagesList.add(message);

            // Notificando thread de ping do destinatario
            userMessagesList.notifyAll();
            
            // Controller.save();
        }
        // Adicionando mensagem ao retorno
        ret.put("sentMessage", message);

        return ret;
    }

    // Função que envia mensagem para todos os usuarios online
    public Payload sendMessage(String author, String messageText){
        // Declarando payload de resposta
        Payload ret = new Payload("SERVER");
        List<Mensagem> sentMessagesList = new ArrayList<Mensagem>();
        
        // Mandando para todos users online
        Usuario authorObj = instance.onlineUsers.get(author);
        for (Usuario recipient : instance.onlineUsers.values()) {
            // Pulando caso seja o autor
            if(recipient.get_username().equals(author)){
                continue;
            }

            // Criando mensagem e colocando na fila
            Mensagem message = new Mensagem(messageText, authorObj, recipient);
            List<Mensagem> userMessagesList = instance.messageQueue.get(recipient.get_username());

            if (userMessagesList == null) {
                userMessagesList = new ArrayList<Mensagem>();
                instance.messageQueue.put(recipient.get_username(), userMessagesList);
            }

            synchronized (userMessagesList) {
                userMessagesList.add(message);
    
                // Notificando thread de ping do destinatario
                userMessagesList.notifyAll();
            }

            sentMessagesList.add(message);
        }
        
        // Adicionando mensagem ao retorno
        System.out.println(messageText);
        if(sentMessagesList.size() == 0){
            ret.put("response", "NOT FOUND");    
        }
        else{
            ret.put("response", "OK");
            ret.put("sentMessages", sentMessagesList);
        }

        return ret;
    }


    // Funções de persistence
    public static void load(){
        instance = Serializer.readFile();
        if(instance == null){
            instance = new Controller(); 
        }

        // Reiniciando lista de usuarios online
        instance.onlineUsers.clear();

    }

    public static void save() {
        Serializer.writeFile(instance);
    }

    public static Controller getController(){
        return instance;
    }
}
