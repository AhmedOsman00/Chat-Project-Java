package chatapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

public class ClientUI extends Application implements Service {

    private Stage stage;

    private static ClientUI instance;

    public ClientUI() {

    }

    public static ClientUI getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        ClientLogFormController clientLogController = (ClientLogFormController) ServiceLocator.getService("clientLogController");
        loader.setController(clientLogController);
        loader.setLocation(getClass().getResource("ClientLogForm.fxml"));
        Parent root = loader.load(getClass().getResource("ClientLogForm.fxml").openStream());
        Scene scene = new Scene(root);
        // primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        stage = primaryStage;
        System.out.println(stage==null);
    }

    public Stage getStage() {
        System.out.println(stage==null);
        return stage;
    }

    public static void main(String[] args) {
        instance = new ClientUI();
        Application.launch();
    }

    @Override
    public String getName() {
        return "main";
    }

    @Override
    public void excute() {

    }
}
