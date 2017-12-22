package server;

import java.rmi.RemoteException;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmiinterfaces.*;

public class ServerImpl extends UnicastRemoteObject implements ServerInt, Service {

    private static final ArrayList <ClientInt> clients = new ArrayList<>();
    private ArrayList <ClientInt> contactList;
    DataBaseConnection dbConn ;
    private static ServerImpl instance;

    private ServerImpl() throws RemoteException {

    }

    public static ServerImpl getInstance() {
        if (instance == null) {
            try {
                instance = new ServerImpl();
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    @Override
    public void tellOthers(Message msg) {
        for (ClientInt clientRef : clients) {
            for (ClientInt clientReciverRef : msg.getReceiverName()) {
                try {
                    if (clientReciverRef.getClient_user_name().equals(clientRef.getClient_user_name())) {
                        try {
                            clientRef.receive(msg);
                            System.out.println(msg);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    @Override
    public ArrayList <ClientInt> register(ClientInt client,String password) {
        try {
            dbConn = (DataBaseConnection) ServiceLocator.getService("dbConn");
            if (dbConn.clientValidate(client.getClient_user_name(), password)) {
                clients.add(client);                
                contactList = dbConn.getContactList();
                contactList.add(dbConn.fillData(client));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contactList;
    }

    @Override
    public void unregister(ClientInt clientRef) {
        clients.remove(clientRef);
    }

    @Override
    public String getName() {
        return "serverImpl";
    }

    @Override
    public void excute() {

    }

    @Override
    public Boolean searchUserName(ClientInt client) throws RemoteException {
        try {
            dbConn = (DataBaseConnection) ServiceLocator.getService("dbConn");
            if (dbConn.clientValidate(client.getClient_user_name())) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
