
package chatapplication;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import rmiinterfaces.Client;
import rmiinterfaces.Message;

public class CreateGrListviewCellFactory implements Callback<ListView<Client>, ListCell<Client>> {

    @Override
    public ListCell<Client> call(ListView<Client> param) {
        return new ListCell<Client>() {
            private HBox contact;
            private VBox nameAndStatus;
            private Circle status;
            private Label contactname;
            private ImageView contactImg;
            private CheckBox checkBox;
            
            private ChatController chatController = (ChatController) ServiceLocator.getService("chatController");

            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                contact = new HBox();
                nameAndStatus = new VBox();
                status = new Circle();
                contactname = new Label();
                contactImg = new ImageView();
                contactname.setText(item.getClient_name());
                contactImg.setImage(new Image(item.getClient_image()));
                checkBox = new CheckBox();
                
                switch (item.getClient_status()) {
                    case "online":
                        status.setFill(Paint.valueOf("GREEN"));
                        break;
                    case "offline":
                        status.setFill(Paint.valueOf("GREY"));
                        break;
                    case "busy":
                        status.setFill(Paint.valueOf("RED"));
                        break;
                    case "away":
                        status.setFill(Paint.valueOf("YELLOW"));
                        break;
                    default:
                        status.setFill(Paint.valueOf("GREEN"));
                        break;
                }
                nameAndStatus.getChildren().addAll(contactname, status);
                contact.getChildren().addAll(contactImg, nameAndStatus, checkBox);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setGraphic(contact);
                }
                setOnMouseClicked((e) -> {
                    contact.setId("contactItem");
                    Message msg = new Message();
                    msg.setReceiverName(item);
                    chatController.setMsgVBox(ChatController.getMsgArea().get(item.getClient_user_name()));
                    try {
                        chatController.createMessage(msg);
                        //contact.setBackground(new BackgroundFill(Paint.valueOf("#9e6ff"), CornerRadii.EMPTY, Insets.EMPTY));
                    } catch (RemoteException ex) {
                        Logger.getLogger(ContactListViewCellFactory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        };
    }

}
