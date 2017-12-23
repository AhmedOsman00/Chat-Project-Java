package chatapplication;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import rmiinterfaces.*;

public class RequestsCbListCellFactory implements Callback<ListView<Client>, ListCell<Client>> {

    @Override
    public ListCell<Client> call(ListView<Client> param) {
        return new ListCell<Client>() {

            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                VBox notifContainer = new VBox();
                HBox topHBox = new HBox();
                HBox bottomHBox = new HBox();
                Button acceptReqBtn = new Button("Accept");
                Button removeReqBtn = new Button("Ignore");
                Label client_name = new Label();
                ImageView reqProfPic = new ImageView();
                ClientImp personalData = (ClientImp) ServiceLocator.getService("clientService");
                CallServerRMI iConnection=(CallServerRMI) ServiceLocator.getService("rmiService");
                
                client_name.setText(item.getClient_name() + "   sent you a friend request");
                reqProfPic.setImage(new Image(item.getClient_image()));
                topHBox.getChildren().addAll(reqProfPic, client_name);
                bottomHBox.getChildren().addAll(acceptReqBtn, removeReqBtn);
                notifContainer.getChildren().addAll(topHBox, bottomHBox);
               
                acceptReqBtn.setOnAction((e) -> {
                    try {
                        personalData.getContactList().add(item);
                        iConnection.responseToRequest(item,personalData.getCurrentClient());
                    } catch (RemoteException ex) {
                        Logger.getLogger(RequestsCbListCellFactory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                removeReqBtn.setOnAction((e) -> {
                    try {
                        iConnection.tellServerToRemove(item.getClient_user_name(),
                                personalData.getCurrentClient().getClient_user_name());
                    } catch (RemoteException ex) {
                        Logger.getLogger(RequestsCbListCellFactory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    setGraphic(null);                    
                });
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setGraphic(notifContainer);
                }
            }
        };
    }

}
