package chatapplication;

import java.rmi.*;
import java.rmi.registry.*;
import java.util.ArrayList;
import java.util.logging.*;
import rmiinterfaces.*;

public class CallServerRMI implements Service {

    private ClientImp personalData= (ClientImp) ServiceLocator.getService("clientService");
    private ServerInt serverInt;
    private static final CallServerRMI instance = new CallServerRMI();

    public ServerInt getServerInt() {
        return serverInt;
    }

    private CallServerRMI() {
        try {
            Registry reg = LocateRegistry.getRegistry(5005);
            serverInt = (ServerInt) reg.lookup("chatService");
        } catch (RemoteException ex) {
            Logger.getLogger(CallServerRMI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(CallServerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMsg(Message msg) {
        try {
            serverInt.tellOthers(msg);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean regToServer(String username, String password) {
        try {
            serverInt.register(username, password,personalData);
            if (personalData.getCurrentClient() == null) {
                return false;
            }           
        } catch (RemoteException ex) {
            Logger.getLogger(CallServerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public static CallServerRMI getInstance() {
        return instance;
    }

    public ArrayList<Client> matchUsers(String userName) throws RemoteException {
        ArrayList<Client> returnMatchUsers = new ArrayList<>();
        returnMatchUsers = serverInt.searchUserName(userName);
        return returnMatchUsers;
    }
    
    public void responseToRequest(Client client,Client currClient){
        try {
            serverInt.addUserName(client,currClient);
        } catch (RemoteException ex) {
            Logger.getLogger(CallServerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public void tellServerToRemove(String reqSender, String reqReceiver) throws RemoteException{
        serverInt.removeRequestFromDB(reqSender, reqReceiver);
    }

    @Override
    public String getName() {
        return "rmiService";
    }

    @Override
    public void excute() {

    }
}
