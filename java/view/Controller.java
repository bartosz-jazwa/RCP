package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.dao.Dao;
import database.dao.EmployeeDaoImpl;
import database.entity.Credentials;
import database.entity.Employee;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private AnchorPane entryPane;
    @FXML
    private JFXTextField userNameInput;
    @FXML
    private JFXPasswordField passInput;
    @FXML
    private JFXButton loginButton;
    @FXML
    private FontAwesomeIconView closeX;
    @FXML
    private Label wrongUserOrPass;
    @FXML
    private void handleButtonAction(ActionEvent actionEvent) {
        closeX.setDisable(true);

        Credentials loginCredentials = new Credentials(userNameInput.getText(),passInput.getText());
        Dao<Employee> dao = new EmployeeDaoImpl();
        Optional<Employee> loggedEmployee = dao.get(loginCredentials);
        loggedEmployee.ifPresent(employee -> {
            closeStage();
            openEntryStage(employee);

        });
        wrongUserOrPass.setVisible(true);
    }
    @FXML
    private void closeLoginWindow(MouseEvent mouseEvent){
        System.exit(1);
    }

    private void closeStage(){
        ((Stage) loginButton.getScene().getWindow()).close();
    }
    private void openEntryStage(Employee employee){
        try {
            //Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Entry.fxml"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Entry.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Entry");
            stage.setScene(new Scene(parent));

            EntryController entryController = loader.getController();
            entryController.initEmployee(employee);
            stage.show();

            stage.setMaximized(true);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wrongUserOrPass.setVisible(false);

    }
}
