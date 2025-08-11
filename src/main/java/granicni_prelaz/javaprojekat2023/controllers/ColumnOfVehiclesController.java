package granicni_prelaz.javaprojekat2023.controllers;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.map.Field;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.util.Utils;
import granicni_prelaz.javaprojekat2023.vehicles.Bus;
import granicni_prelaz.javaprojekat2023.vehicles.Car;
import granicni_prelaz.javaprojekat2023.vehicles.Truck;
import granicni_prelaz.javaprojekat2023.vehicles.Vehicle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ColumnOfVehiclesController implements Initializable {

    @FXML
    private GridPane gpColumnOfVehicles;
    public static GridPane _gpColumnOfVehicles;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _gpColumnOfVehicles = gpColumnOfVehicles;
        initPath();
    }

    public void initPath() {

        List<Field> pathFields = Simulation.pathWithTerminals.getPathFields();

        Utils.setWhiteLabelsIntoMap(pathFields, gpColumnOfVehicles);

        Field field;

        for(int i = Constants.NUMBER_OF_ELEMENTS_AT_FIRST_MAP; i < Constants.NUMBER_OF_VEHICLES + Constants.START_INDEX; i++) {
            field = pathFields.get(i);
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

            gpColumnOfVehicles.add(fieldLabel, field.getYPosition(), field.getXPosition());
        }
    }

    public static void placeEmptyOnPosition(Field field) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label fieldLabel = Utils.createLabel();
                fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.GRAY), null, null)));
                _gpColumnOfVehicles.add(fieldLabel, field.getYPosition(), field.getXPosition());
            }
        });
    }

    public static void placeVehicleOnPosition(Vehicle vehicle , Field field) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label fieldLabel = Utils.createLabel();
                fieldLabel.setText(vehicle.getVehicleName());
                fieldLabel.setTextFill(Paint.valueOf("white"));

                if (vehicle instanceof Car)
                    fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.COLOR_CAR), null, null)));
                if (vehicle instanceof Bus)
                    fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.COLOR_BUS), null, null)));
                if (vehicle instanceof Truck)
                    fieldLabel.setBackground(new Background(new BackgroundFill(Paint.valueOf(Constants.COLOR_TRUCK), null, null)));


                _gpColumnOfVehicles.add(fieldLabel, field.getYPosition(), field.getXPosition());
            }
        });
    }
}
