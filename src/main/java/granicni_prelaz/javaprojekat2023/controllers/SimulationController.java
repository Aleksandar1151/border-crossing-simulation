package granicni_prelaz.javaprojekat2023.controllers;

import granicni_prelaz.javaprojekat2023.HelloApplication;
import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.exceptions.FileLoadingException;
import granicni_prelaz.javaprojekat2023.map.Field;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.terminals.*;
import granicni_prelaz.javaprojekat2023.util.SimulationLogger;
import granicni_prelaz.javaprojekat2023.util.TerminalWatcher;
import granicni_prelaz.javaprojekat2023.util.TimeCounter;
import granicni_prelaz.javaprojekat2023.util.Utils;
import granicni_prelaz.javaprojekat2023.vehicles.Bus;
import granicni_prelaz.javaprojekat2023.vehicles.Car;
import granicni_prelaz.javaprojekat2023.vehicles.Truck;
import granicni_prelaz.javaprojekat2023.vehicles.Vehicle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.awt.Desktop;

public class SimulationController  implements Initializable {
    public static Simulation simulation;
    public static ColumnOfVehiclesController columnOfVehiclesController;
    private static TerminalWatcher watcher;

    public static boolean simulationStarted;
    public static boolean simulationPaused;
    public static boolean simulationFinished;

    public static TimeCounter timeCounter;
    @FXML
    private Label welcomeText;
    @FXML
    private ListView<Label> lvVehicles;
    @FXML
    private Label lblTerminalDescription;

    @FXML
    private GridPane gpColumnOfVehiclesWithTerminals;
    @FXML
    private Label lblTime;

    @FXML
    private Label p1Terminal;
    @FXML
    private Label p2Terminal;
    @FXML
    private Label pkTerminal;
    @FXML
    private Label cTerminal;
    @FXML
    private Label ckTerminal;


    public static Label _p1Terminal;

    public static Label _p2Terminal;

    public static Label _pkTerminal;

    public static Label _cTerminal;

    public static Label _ckTerminal;


    public static GridPane _gpColumnOfVehiclesWithTerminals;

    Stage stage = new Stage();

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        _gpColumnOfVehiclesWithTerminals = gpColumnOfVehiclesWithTerminals;

        _p1Terminal = p1Terminal;
        _p2Terminal = p2Terminal;
        _pkTerminal = pkTerminal;
        _cTerminal = cTerminal;
        _ckTerminal = ckTerminal;




        try {

            setSimulationController();


        } catch (FileLoadingException exception) {
            SimulationLogger.log(this.getClass(), Level.SEVERE, exception.getMessage(), exception);
            System.exit(1);
        }


    }


    public void setSimulationController() throws FileLoadingException {
        simulationStarted = false;
        simulationFinished = false;
        simulationPaused = false;

        simulation = new Simulation();
        watcher = new TerminalWatcher();
        //lblTime.setText("0s");
        //lblTerminalDescription.setText("");
        initPath();
        LoadColumnOfVehiclesView();
        setListViewOfVehicles();
        watcher.start();
    }
    public void initPath() {
        List<Field> pathfields;
        pathfields = Simulation.pathWithTerminals.getPathFields();



        Utils.setWhiteLabelsIntoMapWithTerminals(pathfields, gpColumnOfVehiclesWithTerminals);
        Field field;
        for (int i = 0; i < 10; i++) {
            field = pathfields.get(i);

            Label fieldLabel = Utils.createLabel();






            if (field.isHasVehicle()) {
                if (field.getVehicle() instanceof Bus) {
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getVehicle().getVehicleName(), Constants.COLOR_BUS);
                } else if (field.getVehicle() instanceof Car)
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getVehicle().getVehicleName(), Constants.COLOR_CAR);
                else {
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, field.getVehicle().getVehicleName(), Constants.COLOR_TRUCK);
                }
            }
             else {
                Utils.setLableBackgroundAndBorderColor(fieldLabel, "", Constants.GRAY);

            }

             // POSTAVLJANJE TERMINALA NA MAPU
             switch (i)
             {
                 case 0:
                 case 1:
                     Utils.setLableBackgroundAndBorderColor(fieldLabel, Simulation.customsTerminals.get(i).getTerminalName(), Constants.COLOR_CUSTOMS_TERMINAL);
                     break;
                 case 2:
                     Utils.setLableBackgroundAndBorderColor(fieldLabel, Simulation.policeTerminals.get(0).getTerminalName(), Constants.COLOR_POLICE_TERMINAL);
                     break;
                 case 3:
                     Utils.setLableBackgroundAndBorderColor(fieldLabel, Simulation.policeTerminals.get(1).getTerminalName(), Constants.COLOR_POLICE_TERMINAL);
                     break;
                 case 4:
                     Utils.setLableBackgroundAndBorderColor(fieldLabel, Simulation.policeTerminals.get(2).getTerminalName(), Constants.COLOR_POLICE_TERMINAL);
                     break;
             }







            gpColumnOfVehiclesWithTerminals.add(fieldLabel, field.getYPosition(), field.getXPosition());
        }

    }

    private void setListViewOfVehicles() {
        lvVehicles.getItems().clear();
        for (int i = 0; i < Constants.NUMBER_OF_VEHICLES; i++) {
            Label labelVehicle = new Label("lblVehicle" + i + 1);
            Vehicle vehicle = Simulation.columnOfVehicles.get(i);
            Utils.setLabelListView(labelVehicle, vehicle.getVehicleName(), vehicle instanceof Car ? Constants.COLOR_CAR : (vehicle instanceof Bus ? Constants.COLOR_BUS : Constants.COLOR_TRUCK));
            lvVehicles.getItems().add(labelVehicle);
        }

    }

    @FXML
    public void onLvItemClicked(MouseEvent event) {
        if (lvVehicles.getSelectionModel().getSelectedIndex() != -1) {
            int vehicleIndex = lvVehicles.getSelectionModel().getSelectedIndex();
            Vehicle vehicle = Simulation.columnOfVehicles.get(vehicleIndex);
            //Open new stage
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("vehicleView.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                VehicleController vehicleController = fxmlLoader.getController();
                vehicleController.initInfo(vehicle);



                //stage.setResizable(false);
                stage.centerOnScreen();
                stage.setScene(scene);
                stage.show();
                stage.onCloseRequestProperty().set(event1 -> unselectListViewItem());
            } catch (IOException exception) {
                SimulationLogger.logAsync(getClass(), exception);
            }
        }
    }
    public void unselectListViewItem() {
        lvVehicles.getSelectionModel().clearSelection();
    }


    public void dugmePrikazListeClick(ActionEvent actionEvent) {
    }

    public void startButtonClicked(ActionEvent actionEvent) {

        if (!simulationStarted) {
            if (simulationFinished) {
                try {
                    setSimulationController();
                } catch (FileLoadingException exception) {
                    SimulationLogger.log(this.getClass(), Level.SEVERE, exception.getMessage(), exception);
                }
            }
            timeCounter = new TimeCounter();
            simulation.start();
            timeCounter.start();
            simulationStarted = true;
        } else {
            simulationPaused = !simulationPaused;
            if (!simulationPaused) {
                synchronized (Simulation.pathWithTerminals) {
                    Simulation.pathWithTerminals.notifyAll();
                }

            }
        }

    }
    public static void placeEmptyOnPosition(Field field) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label fieldLabel = Utils.createLabel();
                fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.GRAY), null, null)));
                _gpColumnOfVehiclesWithTerminals.add(fieldLabel, field.getYPosition(), field.getXPosition());
            }
        });
    }

    public static void placeVehicleOnPosition(Vehicle vehicle , Field field)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label fieldLabel = Utils.createLabel();
                fieldLabel.setText(vehicle.getVehicleName());
                fieldLabel.setTextFill(Paint.valueOf("white"));

                if(vehicle instanceof Car)
                    fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.COLOR_CAR), null, null)));
                if(vehicle instanceof Bus)
                    fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.COLOR_BUS), null, null)));
                if(vehicle instanceof Truck)
                    fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.COLOR_TRUCK), null, null)));



                _gpColumnOfVehiclesWithTerminals.add(fieldLabel, field.getYPosition(), field.getXPosition());
            }
        });

    }

    public static void placeTerminalOnPosition(Terminal terminal , Field field)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label fieldLabel = Utils.createLabel();
                fieldLabel.setText(terminal.getTerminalName());
                fieldLabel.setTextFill(Paint.valueOf("white"));

                if(terminal instanceof PoliceTerminal)
                    fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.COLOR_POLICE_TERMINAL), null, null)));
               else
                    fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.COLOR_CUSTOMS_TERMINAL), null, null)));



                _gpColumnOfVehiclesWithTerminals.add(fieldLabel, field.getYPosition(), field.getXPosition());
            }
        });

    }


    public static void changeTextOfTerminal(Terminal terminal, String text)
    {

        switch (terminal.getPosition())
        {
            case 0:
                terminalTextChanger(_cTerminal ,terminal.getTerminalName() + ": " +text);
                break;
            case 1:
                terminalTextChanger(_ckTerminal ,terminal.getTerminalName() + ": " +text);
                break;
            case 2:
                terminalTextChanger(_p1Terminal ,terminal.getTerminalName() + ": " +text);
                break;
            case 3:
                terminalTextChanger(_p2Terminal ,terminal.getTerminalName() + ": " +text);
                break;
            case 4:
                terminalTextChanger(_pkTerminal ,terminal.getTerminalName() + ": " +text);
                break;


        }
    }

    static void terminalTextChanger(Label label, String text)
    {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                label.setText(text);
            }
        });
    }



    private void LoadColumnOfVehiclesView()
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("columnOfVehiclesView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root1, 360, 430));
        } catch (IOException exception) {
            SimulationLogger.logAsync(getClass(), exception);
        }
    }
    @FXML
    void onBtnColumnOfVehiclesClicked(ActionEvent event) {
        stage.show();
    }

    @FXML
    void btnCustomsRecordsClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("customsRecordView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException exception) {
            SimulationLogger.logAsync(getClass(), exception);
        }

    }

    @FXML
    void btnPoliceRecordsClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("policeRecordView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException exception) {
            SimulationLogger.logAsync(getClass(), exception);
        }
    }

    @FXML
    void btnTerminalFileClick(ActionEvent event) {
        String folderPath = "Terminals";
        String fileName = "terminals.txt";

        // Create a File object for the text file
        File textFile = new File(folderPath, fileName);

        // Check if the Desktop API is supported
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();

            // Check if opening files is supported
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                try {
                    desktop.open(textFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Opening files is not supported on this platform.");
            }
        } else {
            System.out.println("Desktop API is not supported on this platform.");
        }
    }

    public void setTimeLabel(int time) {
        Platform.runLater(() -> lblTime.setText(time + "s"));
    }
}