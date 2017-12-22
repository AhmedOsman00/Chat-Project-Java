package server;

public class InitialContext {

    public Object lookup(String jndiName) {
        if (jndiName.equalsIgnoreCase("serverController")) {
            return ServerController.getInstance();
        }else if(jndiName.equalsIgnoreCase("dbConn")) {
            return DataBaseConnection.getInstance();
        }else if(jndiName.equalsIgnoreCase("rmiService")){
            return RMIConnection.getInstance();
        }else if(jndiName.equalsIgnoreCase("serverImpl")){
            return ServerImpl.getInstance();
        }
        return null;
    }
}
