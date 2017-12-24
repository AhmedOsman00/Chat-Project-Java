package chatapplication;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import rmiinterfaces.*;

public class ClientRegController implements Initializable, Service {

    @FXML
    private Label usernameStatus;
    @FXML
    private Label passwordStatus;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField userNameTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private TextField countryTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private RadioButton maleBtn;
    @FXML
    private RadioButton femaleBtn;
    @FXML
    private Button signUpBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private BorderPane clientFormMainPane;
    @FXML
    private HBox topBar;

    private final CallServerRMI iConnection = (CallServerRMI) ServiceLocator.getService("rmiService");
    private ClientRegData clientRegData;

    ToggleGroup genderGroup;

    private static final ClientRegController instance = new ClientRegController();

    private ClientRegController() {
    }

    public static ClientRegController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderGroup = new ToggleGroup();
        genderGroup.getToggles().add(maleBtn);
        maleBtn.setUserData("male");
        genderGroup.getToggles().add(femaleBtn);
        femaleBtn.setUserData("female");
    }

    public void signUp() throws RemoteException {
        clientRegData = new ClientRegData();
        if (!(nameTxt.getText().isEmpty() && userNameTxt.getText().isEmpty() && passwordTxt.getText().isEmpty()
                && addressTxt.getText().isEmpty() && genderGroup.getSelectedToggle() == null)) {
            if ((userNameTxt.getText().length() > 6) && (userNameTxt.getText().matches(""))) {
                if (passwordTxt.getText().length() > 9) {
                    if (!iConnection.checkUserName(userNameTxt.getText())) {
                        clientRegData.setAddress(addressTxt.getText());
                        clientRegData.setPassword(passwordTxt.getText());
                        clientRegData.setClient_name(nameTxt.getText());
                        clientRegData.setClient_user_name(userNameTxt.getText());
                        clientRegData.setCountry(countryTxt.getSelectedText());
                        clientRegData.setGender(genderGroup.getSelectedToggle().getUserData().toString());
                        iConnection.signUp(clientRegData);
                    } else {
                        userNameTxt.setText("");
                        usernameStatus.setText("Not available");
                    }
                } else {
                    passwordTxt.setText("");
                    passwordStatus.setText("Not valid");
                }
            } else {
                userNameTxt.setText("");
                usernameStatus.setText("Not valid");
            }
        }
    }

    public void cancel() throws IOException {
        Parent root =FXMLLoader.load(getClass().getResource("ClientLogForm.fxml"));
        Scene scene=new Scene(root);
        //stage
    }

    @Override
    public String getName() {
        return "clientRegController";
    }

    @Override
    public void excute() {

    }

}
