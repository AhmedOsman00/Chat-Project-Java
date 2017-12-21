package client;



public class Trial {

    public static void main(String[] args) {
        Service service = ServiceLocator.getService("Service1");
        service.excute();
        service = ServiceLocator.getService("Service2");
        service.excute();
        service = ServiceLocator.getService("Service1");
        service.excute();
        service = ServiceLocator.getService("Service2");
        service.excute();
    }
}
