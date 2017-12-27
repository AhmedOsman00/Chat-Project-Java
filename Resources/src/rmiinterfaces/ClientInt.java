package rmiinterfaces;

import java.rmi.*;
import java.util.ArrayList;

public interface ClientInt extends Remote {

    void receiveNotification(String notification) throws RemoteException;

    void receiveMsg(Message msg) throws RemoteException;

    public ArrayList<Client> getContactList() throws RemoteException;

    public void setContactList(ArrayList<Client> contactList) throws RemoteException;

    public Client getCurrentClient() throws RemoteException;

    public void setCurrentClient(Client currentClient) throws RemoteException;

    public ArrayList<Client> getRequestsList() throws RemoteException;

    public void setRequestsList(ArrayList<Client> requestsList) throws RemoteException;
    
    public void addToContactList(Client client) throws RemoteException;
    
    void updateNotifList(Client client) throws RemoteException;   // requests 
}
