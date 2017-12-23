package chatapplication;

import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.util.Callback;
import rmiinterfaces.*;

public class ContactListViewCellFactory implements Callback<ListView<Client>, ListCell<Client>> {

    @Override
    public ListCell<Client> call(ListView<Client> param) {
        return new ListCell<Client>() {
            private HBox contact;
            private VBox nameAndStatus;
            private Circle status;
            private Label contactname;
            private ImageView contactImg;
            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                contactname.setText(item.getClient_name());
                contactImg.setImage(new Image(item.getClient_image()));
                switch(item.getClient_status()){
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
                nameAndStatus.getChildren().addAll(contactname,status);
                contact.getChildren().addAll(contactImg,nameAndStatus);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setGraphic(contact);
                }
                setOnMouseClicked((e)->{
                    contact.setId("contactItem");
                    //contact.setBackground(new BackgroundFill(Paint.valueOf("#9e6ff"), CornerRadii.EMPTY, Insets.EMPTY));
                });
            }
        };
    }

}
