package server;

import java.rmi.RemoteException;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmiinterfaces.*;

public class ServerImpl extends UnicastRemoteObject implements ServerInt, Service {

    private static ArrayList <ClientInt> clients = new ArrayList<>();
    DataBaseConnection DbConn = DataBaseConnection.getInstance();

    public ServerImpl() throws RemoteException {

    }

    @Override
    public void tellOthers(String msg, String name) {
        for (ClientInt clientRef : clients) {
            try {
                clientRef.receive(msg, name);
                System.out.println(name);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void register(ClientInt clientRef) {
        try {
            if(DbConn.clientValidate()){
                clients.add(clientRef);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void unregister(ClientInt clientRef) {
        clients.remove(clientRef);
    }

    @Override
    public String getName() {
        return "ServerImpl";
    }

    @Override
    public void excute() {
        
    }
    
}
