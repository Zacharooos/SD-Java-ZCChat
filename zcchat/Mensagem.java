package zcchat;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

public class Mensagem implements Serializable{
    private final String id = UUID.randomUUID().toString().replace("-", "");
    private final Date timestamp = new Date (System.currentTimeMillis());
    private String text;
    private Usuario emissor;
    private Usuario destinatario;

    public Mensagem(String text, Usuario emissor, Usuario destinatario) {
        super();
        this.text = text;
        this.emissor = emissor;
        this.destinatario = destinatario;
    }
    
    public String get_id(){
        return this.id;
    }

    public String get_text(){
        return this.text;
    }

    public Usuario get_emissor(){
        return this.emissor;
    }
    
    public Usuario get_destinatario(){
        return this.destinatario;
    }

    public Date get_timestamp(){
        return this.timestamp;
    }
}