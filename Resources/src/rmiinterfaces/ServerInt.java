package rmiinterfaces;

import java.rmi.*;
import java.util.ArrayList;

public interface ServerInt extends Remote {
    
    void tellOthers(Message msg) throws RemoteException;

    ArrayList<ClientInt> register(ClientInt ref, String password) throws RemoteException;

    void unregister(ClientInt ref) throws RemoteException;
    
    Boolean searchUserName(ClientInt ref) throws RemoteException;
    
}
