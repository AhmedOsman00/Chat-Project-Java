package rmiinterfaces;

import java.rmi.*;
import java.util.ArrayList;

public interface ServerInt extends Remote {
    
    void sendNotification(String notification) throws RemoteException;
    
    void tellOthers(Message msg) throws RemoteException;

    void register(String username, String password, ClientInt clientRef) throws RemoteException;

    void unregister(ClientInt ref) throws RemoteException;
    
    void addUserName(Client ref,Client currRef) throws RemoteException;
    
    void removeRequestFromDB(String reqSender, String reqReceiver) throws RemoteException;
    
    ArrayList<Client> searchUserName(String userName) throws RemoteException;
    
}
