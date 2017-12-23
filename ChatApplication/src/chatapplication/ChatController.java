package chatapplication;

import java.net.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.geometry.Side;
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
    @FXML
    private ListView<Client> contactListView;
    @FXML
    private ComboBox<String> statusBox;
    @FXML
    private ComboBox<Client> notificationBox;
    @FXML
    private ContextMenu notifiPopMenu;
    @FXML
    private TextField addContactField;
    private ArrayList<Client> matchedUsers;
    private Date date = new Date();
    private Stage primaryStage;
    private double xOffset = 0;
    private double yOffset = 0;
    private static ChatController instance = new ChatController();
    private CallServerRMI iConnection;
    private Client receiverClient;
    private ObservableList<Client> contactObservablelist;
    private ObservableList<Client> notificationsList;
    private ClientImp personalData=(ClientImp) ServiceLocator.getService("clientService");

    private ChatController() {
    }

    public static ChatController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            notificationBox.getButtonCell().setBorder(Border.EMPTY);
            notificationsList = FXCollections.observableArrayList(personalData.getRequestsList());           
            for (Client requestItem : personalData.getRequestsList()) {
                notificationsList.add(requestItem);
            }
            notificationBox.setCellFactory(new RequestsCbListCellFactory());
            notificationBox.setItems(notificationsList);
            colorPicker.setValue(Color.DARKGREY);
            fontTypePicker.getItems().addAll(Font.getFamilies());
            fontSizePicker.getItems().addAll(10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40);
            iConnection = (CallServerRMI) ServiceLocator.getService("rmiService");
            contactObservablelist = FXCollections.observableArrayList(personalData.getContactList());
            for (Client contactItem : personalData.getContactList()) {
                contactObservablelist.add(contactItem);
            }
            contactListView.setCellFactory(new ContactListViewCellFactory());
            contactListView.setItems(contactObservablelist);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            Message msg = new Message();
            msg.setFont_color(colorPicker.getValue().toString());
            msg.setFont_size(fontSizePicker.getValue().toString());
            msg.setFont_type(fontTypePicker.getValue());
            msg.setMsg_context(typeMsgField.getText());
            msg.setMsg_date(date.toString());
            msg.setSenderName(personalData.getCurrentClient());
            //msg.setReceiverName(null);
        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void updateContactList(){
//        
//    }
//    
//    public void updateContactList(){
//        
//    }
//    public void getReciverName(){
//        receiverClient = ;
//    }
    public void searchToAddUsers(KeyEvent e) throws RemoteException {
        if (e.getCode().equals(KeyCode.ENTER)) {
            matchedUsers = iConnection.matchUsers(addContactField.getText());
            if (matchedUsers.isEmpty()) {
                MenuItem notFoundItem=new MenuItem("User not found..");
                notifiPopMenu.getItems().add(notFoundItem);
            } else {
                for (Client matchedUser : matchedUsers) {
                    for (Client contact : personalData.getContactList()) {
                        VBox addAndName = new VBox();
                        HBox notifBox = new HBox();
                        ImageView pic = new ImageView();
                        Label name = new Label(); 
                        name.setText(matchedUser.getClient_name());
                        pic.setImage(new Image(matchedUser.getClient_image()));
                        if (!matchedUser.getClient_user_name().equals(contact.getClient_user_name())) {
                            Button add = new Button("Add");
                            add.setOnAction((event)-> {
                                
                            });
                            addAndName.getChildren().addAll(name, add);
                        }
                        addAndName.getChildren().addAll(name);
                        notifBox.getChildren().addAll(pic,addAndName);
                        CustomMenuItem matchedUserItem = new CustomMenuItem(notifBox);
                        notifiPopMenu.getItems().add(matchedUserItem);
                        notifiPopMenu.show(addContactField, Side.BOTTOM, 0, 0);
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return "chatController";
    }

    @Override
    public void excute() {

    }

}
