package chatapplication;


public class InitialContext {

    public Object lookup(String jndiName) {

        if (jndiName.equalsIgnoreCase("chatController")) {
            return ChatController.getInstance();
        }else if(jndiName.equalsIgnoreCase("rmiService")){
            return CallServerRMI.getInstance();
        }else if(jndiName.equalsIgnoreCase("clientRegController")){
            return CallServerRMI.getInstance();
        }else if(jndiName.equalsIgnoreCase("clientLogController")){
            return ClientLogFormController.getInstance();
        }else if(jndiName.equalsIgnoreCase(" clientServiceclientService")){
            return ClientLogFormController.getInstance();
        }
       
        return null;
    }
}
