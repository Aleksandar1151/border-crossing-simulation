package granicni_prelaz.javaprojekat2023.simulation;

import granicni_prelaz.javaprojekat2023.HelloApplication;
import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.controllers.SimulationController;
import granicni_prelaz.javaprojekat2023.exceptions.FileLoadingException;
import granicni_prelaz.javaprojekat2023.exceptions.MapLoadingException;
import granicni_prelaz.javaprojekat2023.incident.IncidentUtil;
import granicni_prelaz.javaprojekat2023.incident.ListOfPunishedPersons;
import granicni_prelaz.javaprojekat2023.json.PathJsonParser;
import granicni_prelaz.javaprojekat2023.map.Field;
import granicni_prelaz.javaprojekat2023.map.PathOnMap;
import granicni_prelaz.javaprojekat2023.persons.Person;
import granicni_prelaz.javaprojekat2023.terminals.CustomsTerminal;
import granicni_prelaz.javaprojekat2023.terminals.CustomsTerminalForTrucks;
import granicni_prelaz.javaprojekat2023.terminals.PoliceTerminal;
import granicni_prelaz.javaprojekat2023.terminals.PoliceTerminalForTrucks;
import granicni_prelaz.javaprojekat2023.util.SimulationLogger;
import granicni_prelaz.javaprojekat2023.util.TerminalWatcher;
import granicni_prelaz.javaprojekat2023.vehicles.Bus;
import granicni_prelaz.javaprojekat2023.vehicles.Car;
import granicni_prelaz.javaprojekat2023.vehicles.Truck;
import granicni_prelaz.javaprojekat2023.vehicles.Vehicle;
import javafx.application.Platform;

import java.util.*;
import java.util.logging.Level;

public class Simulation extends Thread {

    public static final PathOnMap pathWithTerminals = initPath(Constants.PATH_ON_MAP);
    public static Queue<Vehicle> queueVehicles ;
    public static List<Vehicle> columnOfVehicles;
    public static List<PoliceTerminal> policeTerminals;
    public static List<CustomsTerminal> customsTerminals;
    public static ListOfPunishedPersons policeRecord = new ListOfPunishedPersons();
    public static List<String> customsRecord = new ArrayList<>();
    public static Integer numberOfVehiclesFinished = 0;

    private static PathOnMap initPath(String pathName) {
        PathOnMap pathOnMap = null;
        try {
            pathOnMap = PathJsonParser.getPathFromJson(pathName);
        } catch (MapLoadingException exception) {
            SimulationLogger.log(Simulation.class, Level.SEVERE, exception.getMessage(), exception);
        }
        return pathOnMap;
    }

    public Simulation() {

        columnOfVehicles = new ArrayList<>();
        queueVehicles = new LinkedList<>();

        Car.ID_Counter = 0;
        Bus.ID_Counter = 0;
        Truck.ID_Counter = 0;
        Person.ID_Counter = 0;


        createVehicles(new Car());
        createVehicles(new Bus());
        createVehicles(new Truck());
        createTerminals();
        shuffleQueue();
    }

    public void run()
    {
        // POKRETANJE KOLONE
        for(int i = 0; i<Simulation.columnOfVehicles.size();i++)
        {
            columnOfVehicles.get(i).start();
        }

        while (SimulationController.simulationStarted && !SimulationController.simulationFinished) {


            if (numberOfVehiclesFinished.equals(Constants.NUMBER_OF_VEHICLES)) {
                SimulationController.simulationFinished = true;
            } else {
                synchronized (pathWithTerminals) {
                    if (SimulationController.simulationPaused) {
                        try {
                            pathWithTerminals.wait();
                        } catch (InterruptedException e) {
                            SimulationLogger.log(TerminalWatcher.class, Level.SEVERE, e.getMessage(), e);
                        }
                    }
                  //  refreshMap();
                    //refreshDescription();
                }

            }
        }


        finishGame();

    }

    private void finishGame() {

        System.out.println("Simulacija Gotova");

        SimulationController.simulationStarted = false;
        SimulationController.simulationPaused = false;
        SimulationController.simulationFinished = true;

        IncidentUtil.writeListOfPunishedPersonsIntoFile(policeRecord);
        IncidentUtil.writeCustomsIncident();

        customsRecord.clear();
        policeRecord.getPersons().clear();

        for (Vehicle v : columnOfVehicles) {
            v = null;
        }


        //resetTerminalsState();
        //refreshMap();
       // refreshDescription();

        //SimulationController.timeCounter = null;



    }

    private void refreshDescription() {
        
    }

    private void refreshMap() {
        Platform.runLater(() -> {
                    //HelloApplication.simulationController.initPath();
                    if (SimulationController.columnOfVehiclesController != null)
                        SimulationController.columnOfVehiclesController.initPath();

                    try {
                        HelloApplication.simulationController.setSimulationController();
                    } catch (FileLoadingException e) {
                        SimulationLogger.log(TerminalWatcher.class, Level.SEVERE, e.getMessage(), e);
                    }
                }
        );
    }

    private void createTerminals() {
        policeTerminals = new ArrayList<>();
        customsTerminals = new ArrayList<>();

        customsTerminals.add(new CustomsTerminal("C",0));
        customsTerminals.add(new CustomsTerminalForTrucks("CK",1));
        policeTerminals.add(new PoliceTerminal("P1",2));
        policeTerminals.add(new PoliceTerminal("P2",3));
        policeTerminals.add(new PoliceTerminalForTrucks("PK",4));


    }


    public void createVehicles(Vehicle vehicleType) {

        if (vehicleType instanceof Car)
            for (int i = 0; i < Constants.NUMBER_OF_CARS; i++)
                queueVehicles.add(new Car());


        if (vehicleType instanceof Bus)
            for (int i = 0; i < Constants.NUUMBER_OF_BUSES; i++)
                queueVehicles.add(new Bus());

        if (vehicleType instanceof Truck)
            for (int i = 0; i < Constants.NUMBER_OF_TRUCKS; i++)
                queueVehicles.add(new Truck());

    }

    void shuffleQueue()
    {
        columnOfVehicles = new java.util.ArrayList<>(queueVehicles);
        Collections.shuffle(columnOfVehicles);
        addVehiclesToPath(columnOfVehicles);
        queueVehicles.clear();
        queueVehicles.addAll(columnOfVehicles);

    }

    private void addVehiclesToPath(List<Vehicle> list) {

        List<Field> pathfields = pathWithTerminals.getPathFields();

        for(int i = 0; i<list.size();i++)
        {
            Vehicle vehicle = list.get(i);
            pathfields.get(i+Constants.START_INDEX).setVehicle(vehicle);
            vehicle.setPosition(i+Constants.START_INDEX);


        }

    }
}
