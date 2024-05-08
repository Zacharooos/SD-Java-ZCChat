package zcchat;
import java.util.UUID;

public class Usuario {
    private final String id = UUID.randomUUID().toString().replace("-", "");
    private String username;
    private int status; //Online = 1 // Ofline = 0

    public Usuario(String username) {
        this.username = username;
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
}
