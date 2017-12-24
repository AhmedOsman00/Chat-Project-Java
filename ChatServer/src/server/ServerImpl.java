package server;

import java.rmi.RemoteException;
import java.rmi.server.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.*;
import rmiinterfaces.*;

public class ServerImpl extends UnicastRemoteObject implements ServerInt, Service {
    
    private static final ArrayList<ClientInt> clients = new ArrayList<>();
    private DataBaseConnection dbConn;
    private static ServerImpl instance;
    private ClientInt clientInt;
    
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

//    @Override
//    public void signUp(ClientRegData regData){
//        //call insert client info
//        //call register
//    }
    @Override
    public void tellOthers(Message msg) {//if the other user is offline dont send message to both
        for (ClientInt clientRef : clients) {
            for (Client clientReciverRef : msg.getReceiverName()) {
                try {
                    if (clientReciverRef.getClient_user_name().equals(clientRef.getCurrentClient().getClient_user_name())) {
                        try {
                            clientRef.receiveMsg(msg);
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
    public void register(String username, String password, ClientInt clientRef) {
        try {
            dbConn = (DataBaseConnection) ServiceLocator.getService("dbConn");
            if (dbConn.clientValidate(username, password)) {
                clientRef.setCurrentClient(dbConn.fillData(username));
                clientRef.setContactList(dbConn.getContactList());
                clientRef.setRequestsList(dbConn.getAllRequests());
                clients.add(clientRef);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public ArrayList<Client> searchUserName(String client_user_name) throws RemoteException {
        dbConn = (DataBaseConnection) ServiceLocator.getService("dbConn");
        return dbConn.searchUserName(client_user_name);
    }
    
    @Override
    public void sendNotification(String notification) throws RemoteException {
        
    }
    
    @Override
    public void addUserName(Client ref, Client currRef) throws RemoteException {
        dbConn.acceptContact(ref, currRef);
        for (ClientInt client : clients) {
            if (client.getCurrentClient().getClient_user_name().equals(ref.getClient_user_name())) {
                client.addToContactList(currRef);
            }
        }
    }

    @Override
    public void removeRequestFromDB(String reqSender, String reqReceiver) throws RemoteException {
        try {
            dbConn.removeRequest(reqSender, reqReceiver);
        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addToRequests(Client client, Client currClient) throws RemoteException {
        dbConn.addToRequestsTable(client, currClient);
    }

    @Override
    public Boolean checkUserName(String username) throws RemoteException {
        try {
            if(dbConn.uniqueUserName(username)){
              return true;  
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public void signUp(ClientRegData clientRegData) throws RemoteException {
        dbConn.insertClientInfo(clientRegData);
    }
    
}
