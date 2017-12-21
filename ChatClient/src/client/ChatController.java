package client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;

class ChatController implements Initializable, Service {

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
        primaryStage = (Stage)minimizeIcon.getScene().getWindow();
        colorPicker.setValue(Color.DARKGREY);
        fontTypePicker.getItems().addAll(Font.getFamilies());
        fontSizePicker.getItems().addAll(10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40);
    }

    public void topBarMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    public void topBarMouseDragged(MouseEvent event) {
        primaryStage.setX(event.getScreenX() - xOffset);
        primaryStage.setY(event.getScreenY() - yOffset);
    }

    public void minimizeStage() {
        primaryStage.setIconified(true);
    }

    public void closeStage() {
        primaryStage.close();
    }

    @Override
    public String getName() {
        return "chatController";
    }

    @Override
    public void excute() {

    }

}
