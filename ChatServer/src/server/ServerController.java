package server;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

class ServerController implements Initializable, Service {

    @FXML
    Button serverNotification;
    @FXML
    Button serverStart;
    @FXML
    Button serverStop;
    @FXML
    Button serverStatictics;
    private static ServerController instance = new ServerController();

    private ServerController() {
    }

    public static ServerController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverStart.setShape(new Circle(40));
        serverStart.setMaxSize(80, 80);
        serverStop.setShape(new Circle(40));
        serverStop.setMaxSize(80, 80);
    }

    public void stop() {

    }

    @Override
    public String getName() {
        return "serverController";
    }

    @Override
    public void excute() {

    }

}
