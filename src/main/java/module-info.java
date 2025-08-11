module granicni_prelaz.javaprojekat2023 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires json.simple;
    requires java.desktop;


    opens granicni_prelaz.javaprojekat2023 to javafx.fxml;
    exports granicni_prelaz.javaprojekat2023;
    exports granicni_prelaz.javaprojekat2023.controllers;
    opens granicni_prelaz.javaprojekat2023.controllers to javafx.fxml;
}