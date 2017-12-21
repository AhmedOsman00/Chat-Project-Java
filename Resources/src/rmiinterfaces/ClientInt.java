
package rmiinterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInt extends Remote {

    void receive(String msg, String name) throws RemoteException;
    String getClientName() throws RemoteException;
}
