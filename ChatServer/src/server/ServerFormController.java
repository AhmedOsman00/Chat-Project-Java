
package server;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ServerFormController implements Initializable, Service{

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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup genderGroup = new ToggleGroup();
        genderGroup.getToggles().add(maleBtn);
        genderGroup.getToggles().add(femaleBtn);
    }

    @Override
    public String getName() {
        return "ServerFormController";
    }

    @Override
    public void excute() {
        
    }
    
}
