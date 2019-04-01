package view;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalTime;

public class EntryProperty extends RecursiveTreeObject<EntryProperty> {
    StringProperty startHour;
    StringProperty finishHour;
    StringProperty projectName;
    StringProperty activity;

    public EntryProperty(LocalTime startHour, LocalTime finishHour, String projectName, String activity) {

        this.startHour = new SimpleStringProperty(startHour.toString());
        this.finishHour = new SimpleStringProperty(finishHour.toString());
        this.projectName = new SimpleStringProperty(projectName) ;
        this.activity = new SimpleStringProperty(activity);
    }

    public EntryProperty() {
    }
}
