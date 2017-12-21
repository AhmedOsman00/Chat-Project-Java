
package rmiinterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInt extends Remote {

    void tellOthers(String msg, String name) throws RemoteException;
    void register(ClientInt ref) throws RemoteException;
    void unregister(ClientInt ref) throws RemoteException;

}
