package granicni_prelaz.javaprojekat2023.controllers;

import granicni_prelaz.javaprojekat2023.vehicles.Vehicle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class VehicleController implements Initializable {


    @FXML
    private Label lblVehicleInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initInfo(Vehicle v) {
        lblVehicleInfo.setText(v.toString());
    }
}
