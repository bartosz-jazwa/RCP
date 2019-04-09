package view;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.javafx.collections.ObservableListWrapper;
import database.dao.Dao;
import database.dao.DaoImpl;
import database.dao.EmployeeDaoImpl;
import database.dao.EntryDaoImpl;
import database.entity.Activity;
import database.entity.Employee;
import database.entity.Entry;
import database.entity.Project;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class EntryController implements Initializable {
    @FXML
    private JFXButton logOut;
    @FXML
    private AnchorPane entryPane;
    @FXML
    private HBox hTopBar = new HBox();
    @FXML
    private Label userName;
    @FXML
    private JFXButton timesheetButton;
    @FXML
    private JFXButton delegationButton;
    @FXML
    private Label monthYearLabel = new Label();
    @FXML
    private HBox calendarDay = new HBox();
    @FXML
    private JFXTreeTableView<EntryProperty> treeTableViewer = new JFXTreeTableView<>();
    @FXML
    private MenuItem removeEntryMenuItem;
    @FXML
    private JFXButton addEntry;
    @FXML
    private TableView<Entry> standardTestTable;

    private LocalDate nowDate = LocalDate.now();

    private List<LocalDate> dates = new ArrayList<>();

    private Employee employee;

    private Set<Entry> currentMonthEntries;

    private List<Entry> currentDayEntries;

    private List<Project> projects;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Dao<Project> dao = new DaoImpl<>(Project.class);
        projects = dao.getAll();

        monthYearLabel.setText(nowDate.getMonth().name() + " " + nowDate.getYear());

        for (int i = 1; i <= nowDate.lengthOfMonth(); i++) {
            dates.add(LocalDate.of(nowDate.getYear(), nowDate.getMonth(), i));
        }

        fillDays();

    }

    @FXML
    public void buttonLogOut() {
        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
        employeeDao.flush();
        closeEntry();
        openLogin();

    }

    @FXML
    public void decMonth(MouseEvent mouseEvent) {

        List<Node> nodes = calendarDay.getChildren();
        calendarDay.getChildren().removeAll(nodes);

        nowDate = nowDate.minusMonths(1);
        monthYearLabel.setText(nowDate.getMonth().name() + " " + nowDate.getYear());
        dates = dates.stream()
                .map(localDate -> localDate.minusMonths(1))
                .distinct()
                .collect(Collectors.toList());
        for (int i = dates.size() + 1; i <= nowDate.lengthOfMonth(); i++) {
            dates.add(LocalDate.of(nowDate.getYear(), nowDate.getMonth().getValue(), i));
        }
        fillDays();
        getEntries(nowDate);
        fillTable(currentMonthEntries,nowDate);
    }

    @FXML
    public void incMonth(MouseEvent mouseEvent) {
        List<Node> nodes = calendarDay.getChildren();
        calendarDay.getChildren().removeAll(nodes);

        nowDate = nowDate.plusMonths(1);
        monthYearLabel.setText(nowDate.getMonth().name() + " " + nowDate.getYear());
        dates = dates.stream()
                .map(localDate -> localDate.plusMonths(1))
                .distinct()
                .collect(Collectors.toList());
        for (int i = dates.size() + 1; i <= nowDate.lengthOfMonth(); i++) {
            dates.add(LocalDate.of(nowDate.getYear(), nowDate.getMonth().getValue(), i));
        }
        fillDays();
        getEntries(nowDate);
        fillTable(currentMonthEntries,nowDate);
    }

    private void fillDays() {
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("#95d6d6"), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundFill backgroundFillWhite = new BackgroundFill(Paint.valueOf("White"), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundFill backgroundFillClick = new BackgroundFill(Paint.valueOf("#699999"), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundFill backgroundFillSat = new BackgroundFill(Paint.valueOf("#16e09a"), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundFill backgroundFillSun = new BackgroundFill(Paint.valueOf("#13c587"), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        Background backgroundClick = new Background(backgroundFillClick);
        Background backgroundWhite = new Background(backgroundFillWhite);
        Background backgroundSat = new Background(backgroundFillSat);
        Background backgroundSun = new Background(backgroundFillSun);

        dates.forEach(localDate -> {

            Circle circle = new Circle();
            circle.setRadius(45);

            JFXButton button = new JFXButton();
            button.setMaxHeight(45);
            button.setMinHeight(45);
            button.setMinWidth(45);
            button.setMaxWidth(45);
            button.setShape(circle);

            switch (localDate.getDayOfWeek()) {
                case SATURDAY:
                    button.setBackground(backgroundSat);
                    break;
                case SUNDAY:
                    button.setBackground(backgroundSun);
                    break;
                default:
                    button.setBackground(backgroundWhite);
                    break;
            }

            button.setText(localDate.getDayOfMonth()
                    + "\n"
                    + localDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));

            calendarDay.getChildren().add(button);

            button.setOnMouseClicked(event -> {
                int exception = calendarDay.getChildren().indexOf(button);
                clearBackground(exception, backgroundWhite);

                button.setBackground(backgroundClick);
                fillTable(currentMonthEntries, localDate);
                nowDate = localDate;
            });

            button.setOnMouseEntered(event -> {
                Background currentBackground = button.getBackground();
                if (currentBackground != backgroundClick && localDate.getDayOfWeek() != DayOfWeek.SUNDAY && localDate.getDayOfWeek() != DayOfWeek.SATURDAY) {
                    button.setBackground(background);
                }
            });

            button.setOnMouseExited(event -> {
                Background currentBackground = button.getBackground();
                if (currentBackground != backgroundClick && localDate.getDayOfWeek() != DayOfWeek.SUNDAY && localDate.getDayOfWeek() != DayOfWeek.SATURDAY) {
                    button.setBackground(backgroundWhite);
                }
            });
        });
    }

    private void setUpStandardTestTable(){
        TableColumn<Entry,LocalTime> startHour = new TableColumn<>("Start hour");
        TableColumn<Entry,LocalTime> finishHour = new TableColumn<>("Finish hour");
        TableColumn<Entry, Project> project = new TableColumn<>("Project");
        TableColumn<Entry, Activity> activity = new TableColumn<>("Activity");

        startHour.setCellValueFactory(new PropertyValueFactory<>("startHour"));
        finishHour.setCellValueFactory(new PropertyValueFactory<>("finishHour"));
        project.setCellValueFactory(new PropertyValueFactory<>("project"));
        activity.setCellValueFactory(new PropertyValueFactory<>("activity"));

        ObservableList<Entry> entries = new ObservableListWrapper<>(currentDayEntries);
        standardTestTable.setItems(entries);
        standardTestTable.getColumns().setAll(startHour,finishHour,project,activity);
    }

    private void setUpTable() {

        JFXTreeTableColumn<EntryProperty, String> startHour = new JFXTreeTableColumn("Start hour");

        startHour.setPrefWidth(250);
        startHour.setCellValueFactory((TreeTableColumn.CellDataFeatures<EntryProperty, String> param) -> {
            if (startHour.validateValue(param)) {
                return param.getValue().getValue().startHour;
            } else {
                return startHour.getComputedValue(param);
            }
        });
        startHour.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

        JFXTreeTableColumn<EntryProperty, String> finishHour = new JFXTreeTableColumn("Finish hour");

        finishHour.setPrefWidth(250);
        finishHour.setCellValueFactory((TreeTableColumn.CellDataFeatures<EntryProperty, String> param) -> {
            if (finishHour.validateValue(param)) {
                return param.getValue().getValue().finishHour;
            } else {
                return finishHour.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<EntryProperty, String> projectName = new JFXTreeTableColumn("Project name");

        projectName.setPrefWidth(250);
        projectName.setCellValueFactory((TreeTableColumn.CellDataFeatures<EntryProperty, String> param) -> {
            if (projectName.validateValue(param)) {
                return param.getValue().getValue().projectName;
            } else {
                return projectName.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<EntryProperty, String> activity = new JFXTreeTableColumn("Activity");

        activity.setPrefWidth(250);
        activity.setCellValueFactory((TreeTableColumn.CellDataFeatures<EntryProperty, String> param) -> {
            if (activity.validateValue(param)) {
                return param.getValue().getValue().activity;
            } else {
                return activity.getComputedValue(param);
            }
        });


        JFXTreeTableColumn<EntryProperty, String> action = new JFXTreeTableColumn("What do you wand to do?");

        action.setPrefWidth(250);
        Callback<TreeTableColumn<EntryProperty, String>, TreeTableCell<EntryProperty, String>> actionCellFactory
                = param -> new TreeTableCell<EntryProperty, String>() {

            final JFXButton editButton = new JFXButton();
            final JFXButton deleteButton = new JFXButton();
            final JFXButton addButton = new JFXButton();
            final JFXButton acceptButton = new JFXButton();
            final FontAwesomeIconView editIcon = new FontAwesomeIconView();
            final FontAwesomeIconView deleteIcon = new FontAwesomeIconView();
            final FontAwesomeIconView addIcon = new FontAwesomeIconView();
            final FontAwesomeIconView acceptIcon = new FontAwesomeIconView();
            final HBox box = new HBox();
            final HBox addBox = new HBox();

            {
                editIcon.setGlyphName("CALENDAR_ALT");
                editIcon.setGlyphSize(30);
                deleteIcon.setGlyphName("CALENDAR_TIMES_ALT");
                deleteIcon.setGlyphSize(30);
                editButton.setGraphic(editIcon);
                deleteButton.setGraphic(deleteIcon);
                box.getChildren().addAll(deleteButton);
                addIcon.setGlyphName("CALENDAR_PLUS_ALT");
                addIcon.setGlyphSize(30);
                addButton.setGraphic(addIcon);

                acceptIcon.setGlyphName("CALENDAR_CHECK_ALT");
                acceptIcon.setGlyphSize(30);
                acceptButton.setGraphic(acceptIcon);
                addBox.getChildren().add(addButton);
                addButton.setOnAction(event -> {
                    addEntries();
                    addBox.getChildren().remove(0);
                    addBox.getChildren().add(acceptButton);
                });
            }

            @Override
            public void updateItem(String item, boolean empty) {
                //super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);

                    setText(item);

                } else {

                    editButton.setOnAction(event -> {
                        JFXTreeTableRow<EntryProperty> pr
                                = (JFXTreeTableRow) editButton.getParent().getParent().getParent();
                        int index = pr.getIndex();
                        treeTableViewer.getSelectionModel().select(index);

                    });

                    deleteButton.setOnAction(event -> {
                        JFXTreeTableRow<EntryProperty> pr
                                = (JFXTreeTableRow) deleteButton.getParent().getParent().getParent();
                        int index = pr.getIndex();
                        treeTableViewer.getSelectionModel().select(index);
                        deleteEntry(index);
                    });
                    setGraphic(box);
                    setText(null);
                }
            }

        };
        action.setCellFactory(actionCellFactory);
        treeTableViewer.setShowRoot(false);
        treeTableViewer.getColumns().setAll(startHour, finishHour, projectName, activity, action);

    }

    private void clearBackground(int exception, Background background) {
        BackgroundFill backgroundFillSat = new BackgroundFill(Paint.valueOf("#16e09a"), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundFill backgroundFillSun = new BackgroundFill(Paint.valueOf("#13c587"), CornerRadii.EMPTY, Insets.EMPTY);
        Background backgroundSat = new Background(backgroundFillSat);
        Background backgroundSun = new Background(backgroundFillSun);

        for (int i = 0; i < calendarDay.getChildren().size(); i++) {
            JFXButton button = (JFXButton) calendarDay.getChildren().get(i);
            LocalDate date = LocalDate.of(nowDate.getYear(), nowDate.getMonth(), i + 1);
            if (i != exception && date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                button.setBackground(background);
            } else if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                button.setBackground(backgroundSat);
            } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                button.setBackground(backgroundSun);
            }
        }
    }

    private void closeEntry() {
        ((Stage) logOut.getScene().getWindow()).close();
    }

    private void openLogin() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("Login");
            stage.setScene(new Scene(parent));

            stage.show();

            stage.setMaximized(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void initEmployee(Employee employee) {
        this.employee = employee;
        userName.setText(this.employee.getName());
        setUpTable();

        getEntries(LocalDate.now());
        fillTable(currentMonthEntries, LocalDate.now());
        setUpStandardTestTable();
    }

    private void fillTable(Set<Entry> entries, LocalDate date) {
        currentDayEntries = entries.stream()
                .filter(entry -> entry.getDate().isEqual(date))
                .collect(Collectors.toList());

        List<EntryProperty> propertyList = currentDayEntries.stream()
                .map(entry -> {
                    return new EntryProperty(entry.getStartHour(), entry.getFinishHour(), entry.getProjectName(), entry.getActivityName());
                })
                .sorted(Comparator.comparing(entryProperty -> LocalTime.parse(entryProperty.startHour.getValue()), Comparator.naturalOrder()))
                .collect(Collectors.toList());
        //propertyList.add(new EntryProperty());
        ObservableList<EntryProperty> observableList = new ObservableListWrapper<>(propertyList);
        TreeItem<EntryProperty> root = new RecursiveTreeItem<>(observableList, RecursiveTreeObject::getChildren);

        treeTableViewer.setRoot(root);
        //ObservableList<Entry> obsEntries = new ObservableListWrapper<>(currentDayEntries);
        //standardTestTable.setItems(obsEntries);
    }

    private void getEntries(LocalDate date) {
        EntryDaoImpl entryDao = new EntryDaoImpl();
        currentMonthEntries = entryDao.getByMonth(this.employee, date);
        this.employee.setEntries(currentMonthEntries);
    }

    @FXML
    private void addEntries() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewEntry.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add entry");
            stage.setScene(new Scene(parent));

            AddEntryController entryController = loader.getController();
            entryController.setInitData(employee, projects,nowDate);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            stage.setMaximized(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteEntry(int index) {
        TreeItem<EntryProperty> entryPropertyTreeItem = treeTableViewer.getTreeItem(index);
        EntryProperty entryProperty = entryPropertyTreeItem.getValue();

        LocalTime startTime = LocalTime.parse(entryProperty.startHour.getValue());
        LocalTime finishTime = LocalTime.parse(entryProperty.finishHour.getValue());

        Entry searchedForEntry = currentDayEntries.stream()
                .filter(entry -> entry.getStartHour().equals(startTime) && entry.getFinishHour().equals(finishTime))
                .findAny()
                .get();

        employee.getEntries().remove(searchedForEntry);
        Dao<Entry> entryDao = new DaoImpl<>(Entry.class);
        entryDao.delete(searchedForEntry);
        treeTableViewer.getRoot().getChildren().remove(index);
    }

}
