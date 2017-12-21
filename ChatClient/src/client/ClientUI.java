package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;


public class ClientUI extends Application implements Service {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        ChatController chatController=(ChatController) ServiceLocator.getService("chatController");
        loader.setController(chatController);
        Parent root = FXMLLoader.load(getClass().getResource("clientUI.fxml"));     
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public String getName() {
        return "chatApp";
    }

    @Override
    public void excute() {

    }

}
