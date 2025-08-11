package granicni_prelaz.javaprojekat2023.util;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.controllers.SimulationController;
import granicni_prelaz.javaprojekat2023.map.Field;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;


import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;

public abstract class Utils {
    public static final double FIELD_WIDTH = 40;
    public static final double FIELD_HEIGHT = 40;
    public static final double LIST_WIDTH = 190;


    public static Label createLabel(String text, String color) {

        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        setLableBackgroundAndBorderColor(label, "", color);
        label.setMinWidth(FIELD_WIDTH);
        label.setMinHeight(FIELD_HEIGHT);
        label.setMaxWidth(FIELD_WIDTH);
        label.setMaxHeight(FIELD_HEIGHT);
        label.setTextAlignment(TextAlignment.JUSTIFY);
        label.setAlignment(Pos.CENTER);

        return label;
    }

    public static void createFolderIfNotExists(String path) {
        File folder = Paths.get(path).toFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public static Label createLabel() {

        return createLabel(Constants.GRAY);
    }

    public static void setWhiteLabelsIntoMap(List<Field> pathfields, GridPane gridPane) {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 9; j++) {

                Field field = new Field(i, j);
                if (!pathfields.contains(field)) {
                    Label fieldLabel = Utils.createLabel();
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, "", String.valueOf(Color.WHITE));
                    gridPane.add(fieldLabel, field.getYPosition(), field.getXPosition());
                }
            }
    }

    public static void setWhiteLabelsIntoMapWithTerminals(List<Field> pathfields, GridPane gridPane) {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 5; j++) {

                Field field = new Field(i, j);
                if (!pathfields.contains(field)) {
                    Label fieldLabel = Utils.createLabel();
                    Utils.setLableBackgroundAndBorderColor(fieldLabel, "", String.valueOf(Color.WHITE));
                    gridPane.add(fieldLabel, field.getYPosition(), field.getXPosition());
                }
            }
    }

    public static void setLabelListView(Label label, String content, String color) {
        label.setText(content);
        label.setPadding(new Insets(0, 25, 0, 0));
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Paint.valueOf(Constants.WHITE));
        label.setTextAlignment(TextAlignment.JUSTIFY);
        label.setBackground(new Background(new BackgroundFill(Paint.valueOf(color), null, null)));
        label.setMaxWidth(LIST_WIDTH);
    }


    public static Label createLabel(String color) {
        return createLabel("", color);
    }

    public static void setLableBackgroundAndBorderColor(Label label, String name, String color) {
        label.setText(name);
        label.setTextFill(Paint.valueOf("white"));
        label.setBackground(new Background(new BackgroundFill(Paint.valueOf(color), null, null)));
    }
    public static void checkSimulationPause() {
        synchronized (Simulation.pathWithTerminals) {
            if (SimulationController.simulationPaused) {
                try {
                    Simulation.pathWithTerminals.wait();
                } catch (InterruptedException exception) {
                    SimulationLogger.log(Simulation.class, Level.SEVERE, exception.getMessage(), exception);
                }
            }
        }
    }
}

