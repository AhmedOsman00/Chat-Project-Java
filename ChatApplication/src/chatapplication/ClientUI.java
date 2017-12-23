package chatapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

public class ClientUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        ChatController chatController = (ChatController) ServiceLocator.getService("chatController");
        loader.setController(chatController);
        loader.setLocation(getClass().getResource("clientUI.fxml"));
        Parent root = loader.load(getClass().getResource("clientUI.fxml").openStream());
        Scene scene = new Scene(root);
        // primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
