package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class ServerApplication extends Application implements Service {

    private ServerController serverController ;

    public ServerApplication() {
        
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        ServiceLocator.getService("rmiService");
        serverController = (ServerController) ServiceLocator.getService("serverController");       
        loader.setController(serverController);
        Parent root = loader.load(getClass().getResource("Server.fxml").openStream());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.resizableProperty().set(false);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public String getName() {
        return "server";
    }

    @Override
    public void excute() {

    }
}
