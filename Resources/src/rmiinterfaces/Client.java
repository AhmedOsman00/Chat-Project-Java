package rmiinterfaces;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements Serializable {

    private String client_user_name;
    private String client_status;
    private String client_image;
    private String client_name;

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public Client() throws RemoteException {

    }

    public String getClient_image() {
        return client_image;
    }

    public void setClient_image(String client_image) {
        this.client_image = client_image;
    }

    public String getClient_user_name() {
        return client_user_name;
    }

    public void setClient_user_name(String client_user_name) {
        this.client_user_name = client_user_name;
    }

    public String getClient_status() {
        return client_status;
    }

    public void setClient_status(String client_status) {
        this.client_status = client_status;
    }
}
