package rmiinterfaces;

import java.rmi.*;

public interface ClientInt extends Remote {

    void receive(Message msg) throws RemoteException;

    public String getClient_image() throws RemoteException;

    public void setClient_image(String client_image) throws RemoteException;

    public String getClient_user_name() throws RemoteException;

    public void setClient_user_name(String client_user_name) throws RemoteException;

    public String getClient_status() throws RemoteException;

    public void setClient_status(String client_status) throws RemoteException;

    public String getName() throws RemoteException;

    public void setName(String name) throws RemoteException;

}
