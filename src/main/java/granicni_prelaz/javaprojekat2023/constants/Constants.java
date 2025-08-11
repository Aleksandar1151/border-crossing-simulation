package granicni_prelaz.javaprojekat2023.constants;

import java.io.File;

public abstract class Constants {

    public static final int MAX_CAR_PASSENGER = 4;
    public static final int MAX_BUS_PASSENGER = 51;
    public static final int MAX_TRUCK_PASSENGER = 2;

    public static final int NUMBER_OF_CARS = 35;
    public static final int NUUMBER_OF_BUSES = 5;
    public static final int NUMBER_OF_TRUCKS = 10;

    public static final int SPEED_OF_VEHICLES = 1000;

    public static final int CUSTOMS_TERMINAL_WAIT = 1000;

    public static final String FAJL_TERMINALI = "";
    public static final String FAJL_EVIDENCIJA_NEPRELSAKA = "";
    public static final String FAJL_EVIDENCIJA_KAZNJENIH_OSOBA = "";


    //colors
    public static final String WHITE = "#FFFFFF";
    public static final String BLUE = "#00308F";
    public static final String GRAY = "#bbbbbb";
    public static final String RED = "ce3322";
    public static final String GREEN = "85af38";
    public static final String BRONZE = "#CD7F32";
    public static final String ORANGE = "#CC5500";
    public static final String COLOR_POLICE_TERMINAL = "669bbc";
    public static final String COLOR_CUSTOMS_TERMINAL = "84a59d";
    public static final String POLICE_TRUCK_BLUE = "#082759";

    public static final String COLOR_CAR = "335c67";
    public static final String COLOR_BUS = "e09f3e";
    public static final String COLOR_TRUCK = "540b0e";


    public static final String PATH_ON_MAP = "." + File.separator + "res" + File.separator + "json" + File.separator + "path.json";
    public static final int NUMBER_OF_VEHICLES = 50;
    public static final int NUMBER_OF_ELEMENTS_AT_FIRST_MAP = 10;
    public static final  int NUMBER_OF_TERMINALS = 5;
    public static final int START_INDEX = 5;
    public static final String COLUMN_OF_VEHICLES_VIEW = "columnOfVehiclesView.fxml";
    public static final String DATE_TIME_FORMAT = "dd.MM.yy_hh_mm_ss";
    public static final String RESULTS_DIRECTORY = "." + File.separator + "Records" + File.separator;
    public static final String LIST_OF_PUNISHED_PERSONS_DIRECTORY = RESULTS_DIRECTORY + "PoliceRecords" + File.separator;
    public static final String LIST_OF_PUNISHED_VEHICLES_DIRECTORY = RESULTS_DIRECTORY + "CustomsRecords" + File.separator;

    public static final String TERMINALS_DIRECTORY = "." + File.separator + "Terminals" + File.separator ;
}
