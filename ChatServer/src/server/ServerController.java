package server;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;

public class ServerController implements Initializable, Service {

    @FXML
    private Button serverNotification;
    @FXML
    private Button serverStart;
    @FXML
    private Button serverStop;
    @FXML
    private Button serverStatictics;
    @FXML
    private Label offUsersVal;
    @FXML
    private Label onUsersVal;
    @FXML
    private TextArea notificationTxt;
    private final IntegerProperty offProperty;
    private final IntegerProperty onProperty;
    private static ServerController  instance = new ServerController();
    private final ServerImpl serverImpl;

    private ServerController() {
        offProperty = new SimpleIntegerProperty();
        onProperty = new SimpleIntegerProperty();       
        serverImpl = (ServerImpl) ServiceLocator.getService("serverImpl");
        serverImpl.excute();
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

    public void strat() {

    }

    public void sendNotification() {
        serverImpl.sendNotificationsToAll(notificationTxt.getText());
    }

    public void showStatistics() {

    }

    @Override
    public String getName() {
        return "serverController";
    }

    @Override
    public void excute() {

    }

}
