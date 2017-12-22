package chatapplication;

import java.net.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;
import rmiinterfaces.*;


public class ChatController implements Initializable, Service {

    @FXML
    private ImageView minimizeIcon;
    @FXML
    private ImageView closeIcon;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ComboBox<String> fontTypePicker;
    @FXML
    private ComboBox<Integer> fontSizePicker;
    @FXML
    private BorderPane contactAndMsgPane;
    @FXML
    private HBox bottomBar;
    @FXML
    private HBox topBar;
    @FXML
    private TextField typeMsgField;

    private Date date = new Date();

    private Stage primaryStage;
    
    private double xOffset = 0;
    
    private double yOffset = 0;

    private static ChatController instance = new ChatController();

    private ChatController() {
    }

    public static ChatController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colorPicker.setValue(Color.DARKGREY);
        fontTypePicker.getItems().addAll(Font.getFamilies());
        fontSizePicker.getItems().addAll(10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40);
    }

    public void topBarMousePressed(MouseEvent event) {
        //primaryStage = (Stage) topBar.getScene().getWindow();
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    public void topBarMouseDragged(MouseEvent event) {
        // primaryStage = (Stage) topBar.getScene().getWindow();
        primaryStage.setX(event.getScreenX() - xOffset);
        primaryStage.setY(event.getScreenY() - yOffset);
    }

    public void minimizeStage() {
        //primaryStage = (Stage) topBar.getScene().getWindow();
        primaryStage.setIconified(true);
    }

    public void closeStage() {
        // primaryStage = (Stage) minimizeIcon.getScene().getWindow();
        primaryStage.close();
    }

    public void sendMsg() {
        Message msg = new Message();
        msg.setFont_color(colorPicker.getValue().toString());
        msg.setFont_size(fontSizePicker.getValue().toString());
        msg.setFont_type(fontTypePicker.getValue());
        msg.setMsg_context(typeMsgField.getText());
        msg.setMsg_date(date.toString());
        msg.setSenderName();
        
    }

    @Override
    public String getName() {
        return "chatController";
    }

    @Override
    public void excute() {

    }

}
