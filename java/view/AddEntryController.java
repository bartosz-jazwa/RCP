package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTimePicker;
import database.dao.Dao;
import database.dao.DaoImpl;
import database.entity.Activity;
import database.entity.Employee;
import database.entity.Project;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AddEntryController implements Initializable {
    Employee employee;
    LocalDate date;
    Project project;
    Activity activity;
    List<Project> projects;
    Set<Activity> activities;
    @FXML
    JFXComboBox projectSelector;
    @FXML
    JFXComboBox activitySelector;
    @FXML
    JFXButton acceptButton;
    @FXML
    JFXTimePicker startHourPicker;
    @FXML
    JFXTimePicker finishHourPicker;

    public void setEmployee(Employee employee, LocalDate date) {
        this.employee = employee;
        this.date = date;
    }

    @FXML
    public void selectProject() {
        String projectName = (String) projectSelector.getValue();
        project = projects.stream()
                .filter(project -> project.getName().equals(projectName))
                .findFirst()
                .get();
        activities = project.getActivities();
        activities.forEach(activity -> activitySelector.getItems().add(activity.getName()));
    }
    @FXML
    public void selectActivity(){
        String activityName = (String) activitySelector.getValue();
        activity = activities.stream()
                .filter(activ -> activ.getName().equals(activityName))
                .findFirst()
                .get();
    }
    @FXML
    public void acceptEntry(){
        Dao<Employee> dao = new DaoImpl<>(Employee.class);
        if (this.project!=null && this.activity!=null){
            this.employee.addEntry(this.date,this.startHourPicker.getValue(),finishHourPicker.getValue(),this.project,this.activity);
        }

        dao.save(this.employee);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Dao<Project> dao = new DaoImpl<>(Project.class);
        projects = dao.getAll();


        projects.forEach(project -> {
            projectSelector.getItems().add(project.getName());
        });
        startHourPicker.set24HourView(true);
        startHourPicker.setValue(LocalTime.of(7,0));
        finishHourPicker.set24HourView(true);
        finishHourPicker.setValue(LocalTime.of(15,0));

    }
}
