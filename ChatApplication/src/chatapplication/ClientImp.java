package chatapplication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmiinterfaces.*;

public class ClientImp extends UnicastRemoteObject implements ClientInt, Service {

    private ArrayList<Client> contactList;
    private Client currentClient;
    private ArrayList<Client> requestsList;
    private static ClientImp instance;
   
        
    private ClientImp() throws RemoteException{
    }

    public static ClientImp getInstance() {
        if(instance==null){
            try {
                instance = new ClientImp();
            } catch (RemoteException ex) {
                Logger.getLogger(ClientImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    @Override
    public void receiveNotification(String notification) throws RemoteException {
        
    }

    @Override
    public void receiveMsg(Message msg) throws RemoteException {
        
    }

    @Override
    public ArrayList<Client> getContactList() throws RemoteException {
        return null;
    }

    @Override
    public void setContactList(ArrayList<Client> contactList) throws RemoteException {
        
    }

    @Override
    public Client getCurrentClient() throws RemoteException {
        return null;
    }

    @Override
    public void setCurrentClient(Client currentClient) throws RemoteException {
        
    }

    @Override
    public ArrayList<Client> getRequestsList() throws RemoteException {
        return null;
    }

    @Override
    public void setRequestsList(ArrayList<Client> requestsList) throws RemoteException {
        
    }

    @Override
    public String getName() {
        return "clientService";
    }

    @Override
    public void excute() {
        
    }

    @Override
    public void addToContactList(Client client) throws RemoteException {
        contactList.add(client);
    }

    @Override
    public void updateNotifList(Client client) throws RemoteException {
        requestsList.add(client);
    }
}
