package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmiinterfaces.*;

public class RMIConnection implements Service {

    private static RMIConnection rmi_instance;

    public static RMIConnection getInstance() {
        return rmi_instance;
    }

    private RMIConnection() {
        try {
            rmi_instance = new RMIConnection();
            ServerInt serverObj = (ServerInt) ServiceLocator.getService("serverImpl");
            Registry reg = LocateRegistry.createRegistry(5005);
            reg.rebind("chatService", serverObj);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "rmiService";
    }

    @Override
    public void excute() {

    }

}
