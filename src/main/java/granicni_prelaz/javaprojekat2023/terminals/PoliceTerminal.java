package granicni_prelaz.javaprojekat2023.terminals;

import granicni_prelaz.javaprojekat2023.controllers.SimulationController;
import granicni_prelaz.javaprojekat2023.gadgets.IdentificationDocument;
import granicni_prelaz.javaprojekat2023.persons.Passenger;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.vehicles.Bus;
import granicni_prelaz.javaprojekat2023.vehicles.Truck;
import granicni_prelaz.javaprojekat2023.vehicles.Vehicle;

import java.util.ArrayList;

public class PoliceTerminal extends Terminal {

    public PoliceTerminal(String terminalName, int position) {
        super(terminalName,  position);
    }




    @Override
    public void processVehicle() throws InterruptedException {

        Vehicle vehicle = this.vehicle;
        printInfo("Obrađuje vozilo: " + vehicle.getVehicleName());



        if (vehicle instanceof Bus)
            Thread.sleep(100);
        else
            Thread.sleep(500);

        IdentificationDocument identificationDocument = vehicle.getDriver().getIdentificationDocument();

        if (!identificationDocument.isValid())
            ejectVehicle();
        else {

            for (Passenger passenger : new ArrayList<>(vehicle.getPassengers()) ) {
                identificationDocument = passenger.getIdentificationDocument();
                if (!identificationDocument.isValid())
                    ejectPassenger(passenger);
            }


        }
        printInfo("Završena obrada vozila: " + vehicle.getVehicleName());

    }

    @Override
    public boolean acceptVehicle(Vehicle vehicleType) {
        return (vehicleType instanceof Truck) ? false : true;
    }

    @Override
    void ejectPassenger(Passenger passenger) {
        vehicle.getPassengers().remove(passenger);
        Simulation.policeRecord.addPerson(passenger);



    }

    @Override
    void ejectVehicle() {


        vehicle.setEjected(true);
        Simulation.policeRecord.addPerson(vehicle.getDriver());
        for (Passenger passenger: new ArrayList<>(vehicle.getPassengers()) ) {
            ejectPassenger(passenger);
        }
        setVehicle(null);
        if(Simulation.queueVehicles.peek() == vehicle)
            Simulation.queueVehicles.remove();
    }
    @Override
    public void printInfo(String text) {
        SimulationController.changeTextOfTerminal(this, text);
    }


    @Override
    public void setInFunction(boolean inFunction) {
        super.setInFunction(inFunction);
        printInfo((inFunction) ? "U funkciji" : "Nije u funkciji.");
    }
}
