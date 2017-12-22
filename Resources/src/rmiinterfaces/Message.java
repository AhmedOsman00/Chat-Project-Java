
package rmiinterfaces;


import java.io.Serializable;
import java.util.*;

public class Message implements Serializable{
    
    private ArrayList <Client> receiverName ;
    private Client senderName;
    private String font_type;
    private String font_size;
    private String font_color;
    private String msg_context;
    private String msg_date;

    @Override
    public String toString() {
        return "Message{" + "receiverName=" + receiverName + ", senderName=" + senderName + ", font_type=" + font_type + ", font_size=" + font_size + ", font_color=" + font_color + ", msg_context=" + msg_context + ", msg_date=" + msg_date + '}';
    }

    public ArrayList<Client> getReceiverName() {
        return receiverName;
    }

    public Client getSenderName() {
        return senderName;
    }

    public String getFont_type() {
        return font_type;
    }

    public String getFont_size() {
        return font_size;
    }

    public String getFont_color() {
        return font_color;
    }

    public String getMsg_context() {
        return msg_context;
    }


    public void setReceiverName(Client client) {
        if(receiverName==null){
            receiverName=new ArrayList<>();
        }
        this.receiverName.add(client);
    }

    public void setSenderName(Client senderName) {
        this.senderName = senderName;
    }

    public void setFont_type(String font_type) {
        this.font_type = font_type;
    }

    public void setFont_size(String font_size) {
        this.font_size = font_size;
    }

    public void setFont_color(String font_color) {
        this.font_color = font_color;
    }

    public void setMsg_context(String msg_context) {
        this.msg_context = msg_context;
    }

    public String getMsg_date() {
        return msg_date;
    }

    public void setMsg_date(String msg_date) {
        this.msg_date = msg_date;
    }

    
   
} 