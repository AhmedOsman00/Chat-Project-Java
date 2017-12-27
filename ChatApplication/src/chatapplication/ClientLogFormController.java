package chatapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ClientLogFormController implements Initializable, Service {//remove from service locator

    @FXML
    private Hyperlink signupLink;
    @FXML
    private Hyperlink signinLink;
    @FXML
    private TextField userNameTxt;
    @FXML
    private TextField passwordTxt;
    private static ClientLogFormController instance = new ClientLogFormController();
    private final CallServerRMI iConnection;
    private Stage primaryStage;
    private ClientUI main;

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

    public void signUp() throws IOException { 
        primaryStage = main.getStage();
        FXMLLoader loader = new FXMLLoader();
        ClientLogFormController clientLogFormController = (ClientLogFormController) ServiceLocator.
                getService("clientLogController");
        loader.setController(clientLogFormController);
        loader.setLocation(getClass().getResource("ClientLogForm.fxml"));
        Parent root = loader.load(getClass().getResource("ClientLogForm.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }

    public void signIn() throws IOException {
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
