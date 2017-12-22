package chatapplication;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmiinterfaces.*;


public class InitialContext {

    public Object lookup(String jndiName) {

        if (jndiName.equalsIgnoreCase("chatController")) {
            return ChatController.getInstance();
        }else if(jndiName.equalsIgnoreCase("rmiService")){
            return RMIConnection.getInstance();
        }else if(jndiName.equalsIgnoreCase("clientRegController")){
            return RMIConnection.getInstance();
        }else if(jndiName.equalsIgnoreCase("clientLogController")){
            return ClientLogFormController.getInstance();
        }else if(jndiName.equalsIgnoreCase("clientService")){
            try {
                return new Client();
            } catch (RemoteException ex) {
                Logger.getLogger(InitialContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
}
