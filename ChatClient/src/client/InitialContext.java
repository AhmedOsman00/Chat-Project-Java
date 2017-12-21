package client;




import client.*;

public class InitialContext {

    public Object lookup(String jndiName) {

        if (jndiName.equalsIgnoreCase("chatController")) {
            return ChatController.getInstance();
        }
        return null;
    }
}
