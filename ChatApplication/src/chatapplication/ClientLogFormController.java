package chatapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ClientLogFormController implements Initializable, Service {//remove from service locator

    @FXML
    private Button signInBtn;
    @FXML
    private Button signOutBtn;
    @FXML
    private TextField userNameTxt;
    @FXML
    private TextField passwordTxt;
    private static final ClientLogFormController instance = new ClientLogFormController();
    private final CallServerRMI iConnection;
    private Stage primaryStage;
    private final ClientUI main;

    private ClientLogFormController() {
        main = (ClientUI) ServiceLocator.getService("main");
        iConnection = (CallServerRMI) ServiceLocator.getService("rmiService");
    }

    public static ClientLogFormController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public String getName() {
        return "clientLogController";
    }

    public void signUp() {
        try {
            primaryStage = (Stage) userNameTxt.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            ClientRegController clientRegController = (ClientRegController) ServiceLocator.
                    getService("clientRegController");
            loader.setController(clientRegController);
            loader.setLocation(getClass().getResource("ClientRegFrom.fxml"));
            Parent root = loader.load(getClass().getResource("ClientRegFrom.fxml").openStream());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(ClientLogFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void signIn() throws IOException {
        primaryStage = (Stage) userNameTxt.getScene().getWindow();
        if (iConnection.regToServer(userNameTxt.getText(), passwordTxt.getText())) {
            FXMLLoader loader = new FXMLLoader();
            ChatController chatController = (ChatController) ServiceLocator.
                    getService("chatController");
            loader.setController(chatController);
            loader.setLocation(getClass().getResource("ClientUI.fxml"));
            Parent root = loader.load(getClass().getResource("ClientUI.fxml").openStream());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } else {
            //not valid
        }

    }

    @Override
    public void excute() {

    }

}
