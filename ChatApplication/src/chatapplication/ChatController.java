package chatapplication;

import java.net.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.*;
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
    @FXML
    private ListView<Client> contactListView;
    @FXML
    private ComboBox<String> statusBox;
    @FXML
    private Button reqNotifications;
    private ContextMenu reqNotifiPop;
    private ContextMenu notifiPopMenu;
    @FXML
    private TextField addContactField;
    @FXML
    private ComboBox<String> otherNotifications;
    @FXML
    private ScrollPane messageScrollPane;
    @FXML
    private Button createGrBtn;

    private ArrayList<Client> matchedUsers;
    private Stage primaryStage;
    private double xOffset = 0;
    private double yOffset = 0;
    private static final ChatController instance = new ChatController();
    private CallServerRMI iConnection;
    private Client receiverClient;
    private ObservableList<Client> contactObservablelist;
    private ObservableList<Client> notificationsList;
    private ObservableList<String> otherNotificationsList;
    private ClientImp personalData;
    private Message message;
    private static HashMap<String, VBox> vBoxesOfMsgs;
    private String lastClientName;
    private Boolean flag;

    public static HashMap<String, VBox> getMsgArea() {
        return vBoxesOfMsgs;
    }

    private ChatController() {

    }

    public static ChatController getInstance() {
        return instance;
    }

    public void setContactObservablelist(Client client) {
        Platform.runLater(() -> {
            this.contactObservablelist.add(client);
            vBoxesOfMsgs.put(client.getClient_user_name(), new VBox());
        });
    }

    public void setNotificationsList(Client client) {
        Platform.runLater(() -> {
            this.notificationsList.add(client);
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            personalData = (ClientImp) ServiceLocator.getService("clientService");
            vBoxesOfMsgs = new HashMap<>();
            notificationsList = FXCollections.observableArrayList(personalData.getRequestsList());
            colorPicker.setValue(Color.DARKGREY);
            fontTypePicker.getItems().addAll(Font.getFamilies());
            fontSizePicker.getItems().addAll(10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40);
            iConnection = (CallServerRMI) ServiceLocator.getService("rmiService");
            contactObservablelist = FXCollections.observableArrayList(personalData.getContactList());
            for (Client contactItem : personalData.getContactList()) {
                vBoxesOfMsgs.put(contactItem.getClient_user_name(), new VBox());
            }
            contactListView.setCellFactory(new ContactListViewCellFactory());
            contactListView.setItems(contactObservablelist);
            otherNotificationsList = FXCollections.observableArrayList();
            otherNotifications.setItems(otherNotificationsList);
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

    public void sendMsg(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            message.setMsg_context(typeMsgField.getText());
            iConnection.sendMsg(message);
        }
    }

    public void searchToAddUsers(KeyEvent e) throws RemoteException {
        if (e.getCode().equals(KeyCode.ENTER)) {
            notifiPopMenu = new ContextMenu();
            matchedUsers = iConnection.matchUsers(addContactField.getText());
            if (matchedUsers.isEmpty()) {
                MenuItem notFoundItem = new MenuItem("User not found..");
                notifiPopMenu.getItems().add(notFoundItem);
            } else {
                for (Client matchedUser : matchedUsers) {
                    VBox addAndName = new VBox();
                    HBox notifBox = new HBox();
                    ImageView pic = new ImageView();
                    Label name = new Label();
                    Label reqSent = new Label("Request Sent");
                    name.setText(matchedUser.getClient_name());
                    // pic.setImage(new Image(matchedUser.getClient_image()));
                    for (Client contact : personalData.getContactList()) {
                        if (!(matchedUser.getClient_user_name().equals(contact.getClient_user_name())
                                && matchedUser.getClient_user_name().equals(personalData.getCurrentClient()
                                        .getClient_user_name()))) {
                            Button add = new Button("Add");
                            add.setOnAction((event) -> {                           
                                addAndName.getChildren().remove(add);
                                addAndName.getChildren().add(reqSent);
                                notifiPopMenu.show(addContactField.getScene().getWindow(), 300, 300);
                                try {
                                    iConnection.tellServerToAdd(matchedUser, personalData.getCurrentClient());
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                            addAndName.getChildren().addAll(name, add);
                        }else{
                            addAndName.getChildren().addAll(name);
                        }
                    }                    
                    notifBox.getChildren().addAll(pic, addAndName);
                    CustomMenuItem matchedUserItem = new CustomMenuItem(notifBox);
                    notifiPopMenu.getItems().add(matchedUserItem);
                }
            }
            notifiPopMenu.show(addContactField.getScene().getWindow(),
                    addContactField.getScene().getWindow().getX() + addContactField.getLayoutX() + 290,
                    addContactField.getScene().getWindow().getY() + addContactField.getLayoutY() + addContactField.getHeight() + 65);
        }
    }

    public void updateOtherNotifications(String notification) {
        Platform.runLater(() -> {
            otherNotificationsList.add(notification);
        });
    }

    public void createMessage(Message msg) throws RemoteException {
        msg.setSenderName(personalData.getCurrentClient());
        //msg.setFont_color(colorPicker.getValue().toString());
        //msg.setFont_type(fontTypePicker.getValue());
//        msg.setFont_size(fontSizePicker.getValue().toString());        
        //msg.setMsg_date(LocalDateTime.now().toString());
        message = msg;
    }

    public void setMsgVBox(VBox vBox) {
        messageScrollPane.setContent(vBox);
    }

    public void displayMsg(Message msg) {
        HBox msgLabel = new HBox();
        Label msgText = new Label();
        HBox firstMsg = new HBox();
        ImageView imgClient;
        msgLabel.setSpacing(3.0);
        Platform.runLater(() -> {
            try {
                if (msg.getReceiverName().getClient_user_name().equals(personalData.getCurrentClient().getClient_user_name())) {
                    vBoxesOfMsgs.get(msg.getSenderName().getClient_user_name()).getChildren().add(new Label(msg.getMsg_context()));
                } else {
                    vBoxesOfMsgs.get(msg.getReceiverName().getClient_user_name()).getChildren().add(new Label(msg.getMsg_context()));
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
//        if (m.getClientName().equalsIgnoreCase(name)) {
//            imgClient = new ImageView(m.getClientImage());
//            msgLabel.setAlignment(Pos.CENTER_RIGHT);
//            msgText.getStyleClass().add("downRight");
//            firstMsg.setAlignment(Pos.CENTER_RIGHT);
//            msgLabel.getChildren().addAll(msgText, imgClient);
//            if (flag && lastClientName != null) {
//                msgTemp.getChildren().get(0).getStyleClass().clear();
//                msgTemp.getChildren().get(0).getStyleClass().add("upRight");
//                flag = false;
//            } else if (lastClientName != null) {
//                msgTemp.getChildren().get(0).getStyleClass().clear();
//                msgTemp.getChildren().get(0).getStyleClass().add("middleRight");
//            }
//        } else {
//            imgClient = new ImageView(getNameImage(name));
//            msgLabel.setAlignment(Pos.CENTER_LEFT);
//            msgText.getStyleClass().add("downLeft");
//            firstMsg.setAlignment(Pos.CENTER_LEFT);
//            msgLabel.getChildren().addAll(imgClient, msgText);
//            if (flag && lastClientName != null) {
//                msgTemp.getChildren().get(1).getStyleClass().clear();
//                msgTemp.getChildren().get(1).getStyleClass().add("upLeft");
//                flag = false;
//            } else if (lastClientName != null) {
//                msgTemp.getChildren().get(1).getStyleClass().clear();
//                msgTemp.getChildren().get(1).getStyleClass().add("middleLeft");
//            }
//        }
//        imgClient.getStyleClass().add("pic");
//        imgClient.setFitHeight(20);
//        imgClient.setFitWidth(20);
////        imgClient.setClip(clip);
//        imgClient.setClip(null);
//        imgClient.setEffect(new DropShadow(20, Color.BLACK));
//        if (lastClientName == null) {
//            Label client = new Label(name);
//            firstMsg.getChildren().add(client);
//            msgText.setText(msg);
//            msgText.getStyleClass().add("firstMsg");
//            Platform.runLater(() -> {
//                chatArea.getChildren().addAll(firstMsg, msgLabel);
//            });
//            flag = true;
//            msgTemp = msgLabel;
//            msgImage = imgClient;
//            lastClientName = name;
//        } else if (!lastClientName.equalsIgnoreCase(name)) {
//            Label client = new Label(name);
//            firstMsg.getChildren().add(client);
//            msgText.setText(msg);
//            msgText.getStyleClass().add("firstMsg");
//            Platform.runLater(() -> {
//                chatArea.getChildren().addAll(firstMsg, msgLabel);
//            });
//            flag = true;
//            msgTemp = msgLabel;
//            msgImage = imgClient;
//            lastClientName = name;
//        } else if (lastClientName.equalsIgnoreCase(name)) {
//            msgText.setText(msg);
//            Platform.runLater(() -> {
//                msgTemp.getChildren().remove(msgImage);
//                ImageView imgx = new ImageView();
//                imgx.setFitHeight(20);
//                imgx.setFitWidth(20);
//                if (m.getClientName().equalsIgnoreCase(name)) {
//                    msgTemp.getChildren().add(1, imgx);
//                } else {
//                    msgTemp.getChildren().add(0, imgx);
//                }
//                chatArea.getChildren().addAll(msgLabel);
//                msgTemp = msgLabel;
//                msgImage = imgClient;
//            });
//        }
    }

    public void createGroup() {
        //contactListView.setCellFactory(new CreateGrListviewCellFactory());
        contactListView.setItems(contactObservablelist);
    }

    public void retriveRequests() {
        reqNotifiPop = new ContextMenu();
        for (Client item : notificationsList) {
            VBox notifContainer = new VBox();
            HBox topHBox = new HBox();
            HBox bottomHBox = new HBox();
            Button acceptReqBtn = new Button("Accept");
            Button removeReqBtn = new Button("Ignore");
            Label client_name = new Label();
            ImageView reqProfPic = new ImageView();
            client_name.setText(item.getClient_name() + " sent you a friend request");
            // reqProfPic.setImage(new Image(item.getClient_image()));
            topHBox.getChildren().addAll(reqProfPic, client_name);
            bottomHBox.getChildren().addAll(acceptReqBtn, removeReqBtn);
            notifContainer.getChildren().addAll(topHBox, bottomHBox);
            CustomMenuItem matchedUserItem = new CustomMenuItem(notifContainer);
            reqNotifiPop.getItems().add(matchedUserItem);
            reqNotifiPop.show(reqNotifications.getScene().getWindow(),
                    reqNotifications.getScene().getWindow().getX() + reqNotifications.getLayoutX() + 290,
                    reqNotifications.getScene().getWindow().getY() + reqNotifications.getLayoutY() + reqNotifications.getHeight() + 65);
            acceptReqBtn.setOnAction((e) -> {
                try {
                    System.out.println("accept request");
                    personalData.getContactList().add(item);
                    iConnection.responseToRequest(item, personalData.getCurrentClient());
                    reqNotifiPop.hide();
                    notificationsList.remove(item);
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            removeReqBtn.setOnAction((e) -> {
                try {
                    System.out.println("ignore request");
                    iConnection.tellServerToRemove(item.getClient_user_name(),
                            personalData.getCurrentClient().getClient_user_name());
                    reqNotifiPop.hide();
                    notificationsList.remove(item);
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
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
