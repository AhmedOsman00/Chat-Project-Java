package chatapplication;

import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.RemoteException;
import java.util.logging.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.util.Callback;
import javax.imageio.ImageIO;
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
            private ChatController chatController = (ChatController) ServiceLocator.getService("chatController");

            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                contact = new HBox();
                nameAndStatus = new VBox();
                status = new Circle();
                contactname = new Label();
                contactImg = new ImageView();
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    try {
                        contactname.setText(item.getClient_name());
                        if (item.getClient_image() != null) {
                            InputStream in = new ByteArrayInputStream(item.getClient_image());
                            BufferedImage bufferedImage = ImageIO.read(in);
                            Image clientImage = SwingFXUtils.toFXImage(bufferedImage, null);
                            contactImg.setImage(clientImage);
                        }
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
                        setOnMouseClicked((e) -> {
                            // contact.setId("contactItem");
                            try {
                                Message msg = new Message();
                                msg.setReceiverName(item);
                                chatController.setMsgVBox(ChatController.getMsgArea().get(item.getClient_user_name()));
                                chatController.createMessage(msg);
                                //contact.setBackground(new BackgroundFill(Paint.valueOf("#9e6ff"), CornerRadii.EMPTY, Insets.EMPTY));
                            } catch (RemoteException ex) {
                                Logger.getLogger(ContactListViewCellFactory.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        nameAndStatus.getChildren().addAll(contactname, status);
                        contact.getChildren().addAll(contactImg, nameAndStatus);
                        setGraphic(contact);
                    } catch (IOException ex) {
                        Logger.getLogger(ContactListViewCellFactory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
    }

}
