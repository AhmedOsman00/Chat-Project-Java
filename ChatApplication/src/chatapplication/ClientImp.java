package chatapplication;

import java.rmi.RemoteException;
import java.util.ArrayList;
import rmiinterfaces.*;

public class ClientImp implements ClientInt, Service {

    private ArrayList<Client> contactList;
    private Client currentClient;
    private ArrayList<Client> requestsList;
    private static ClientImp instance = new ClientImp();
   
        
    private ClientImp() {
    }

    public static ClientImp getInstance() {
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
}
