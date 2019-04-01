package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    Stage loginStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.loginStage = primaryStage;

        Parent loginLoader = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));

        loginStage.setTitle("Login");
        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.setScene(new Scene(loginLoader, 600, 400));
        loginStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
