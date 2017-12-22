package chatapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ClientRegController implements Initializable, Service {

    @FXML
    private TextField userNameTxt;
    @FXML
    private TextField emailTxt;
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

    private static ClientRegController instance = new ClientRegController();

    private ClientRegController() {
    }

    public static ClientRegController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup genderGroup = new ToggleGroup();
        genderGroup.getToggles().add(maleBtn);
        genderGroup.getToggles().add(femaleBtn);
    }

    public void signUp() {

    }

    public void cancel() {

    }

    @Override
    public String getName() {
        return "clientRegController";
    }

    @Override
    public void excute() {

    }

}
