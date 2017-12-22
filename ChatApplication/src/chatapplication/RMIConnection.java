package chatapplication;

import java.rmi.*;
import java.rmi.registry.*;
import java.util.ArrayList;
import java.util.logging.*;
import rmiinterfaces.*;

public class RMIConnection implements Service {

    private ArrayList<ClientInt> contactList;
    private ServerInt serverInt;
    private static final RMIConnection instance = new RMIConnection();

    public ServerInt getServerInt() {
        return serverInt;
    }

    private RMIConnection() {
        try {
            Registry reg = LocateRegistry.getRegistry(5005);
            serverInt = (ServerInt) reg.lookup("chatService");
        } catch (RemoteException ex) {
            Logger.getLogger(RMIConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(RMIConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMsg(Message msg) {
        try {
            serverInt.tellOthers(msg);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean regToServer(Client client, String password) {
        try {
            contactList = serverInt.register(client, password);
            if (contactList.get(contactList.size() - 1).getClient_status() == null) {
                return false;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(RMIConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        //(Client)ServiceLocator.getService("clientService") = contactList.get(contactList.size() - 1);
        return true;
    }

    public static RMIConnection getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return "rmiService";
    }

    @Override
    public void excute() {

    }
}
