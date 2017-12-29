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
    private ChatController chatController=(ChatController) ServiceLocator.getService("chatController");
   
        
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
        chatController.updateOtherNotifications(notification);
    }

    @Override
    public void receiveMsg(Message msg) throws RemoteException {
        chatController.displayMsg(msg);
    }

    @Override
    public ArrayList<Client> getContactList() throws RemoteException {
        return contactList;
    }

    @Override
    public void setContactList(ArrayList<Client> contactList) throws RemoteException {
        this.contactList = contactList;
    }

    @Override
    public Client getCurrentClient() throws RemoteException {
        return currentClient;
    }

    @Override
    public void setCurrentClient(Client currentClient) throws RemoteException {        
        this.currentClient = currentClient;
    }

    @Override
    public ArrayList<Client> getRequestsList() throws RemoteException {
        return requestsList;
    }

    @Override
    public void setRequestsList(ArrayList<Client> requestsList) throws RemoteException {
        this.requestsList = requestsList;
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
        chatController.setContactObservablelist(client);
    }

    @Override
    public void updateNotifList(Client client) throws RemoteException {
        requestsList.add(client);
        chatController.setNotificationsList(client);
    }
}
