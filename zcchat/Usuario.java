package zcchat;
import java.io.Serializable;
import java.util.UUID;

public class Usuario implements Serializable{
    private final String id = UUID.randomUUID().toString().replace("-", "");
    private String username;
    private String password;
    private int status; //Online = 1 // Ofline = 0

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String get_username(){
        return this.username;
    }

    public String get_id(){
        return this.id;
    }

    public int get_status(){
        return this.status;
    }

    public void turn_status(int state){
        this.status = state;
    }

    public boolean validatePassword(String input){
        if(input.equals(password)){
            return true;
        }
        else{
            return false;
        }
    }

    public void changePassword(String newPassword){
        this.password = newPassword;
    }
}
