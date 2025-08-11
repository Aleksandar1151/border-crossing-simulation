package granicni_prelaz.javaprojekat2023.util;

import granicni_prelaz.javaprojekat2023.HelloApplication;
import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.controllers.SimulationController;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;

import java.util.logging.Level;

public class TimeCounter extends  Thread {

    private int time = 0;

    @Override
    public void run() {

        while(!SimulationController.simulationFinished) {
            synchronized (Simulation.pathWithTerminals) {
                if (SimulationController.simulationPaused) {
                    try {
                        Simulation.pathWithTerminals.wait();
                    } catch (InterruptedException exception) {
                        SimulationLogger.log(getClass(), Level.SEVERE, exception.getMessage(), exception);
                    }

                }
            }
            HelloApplication.simulationController.setTimeLabel(time);
            try {
                Thread.sleep(Constants.SPEED_OF_VEHICLES);
            } catch (InterruptedException exception) {
                SimulationLogger.log(getClass(), Level.SEVERE,exception.getMessage(),exception);
            }
            time++;
        }
    }

    public int getTime() {
        return time;
    }
}
