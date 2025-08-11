package granicni_prelaz.javaprojekat2023.terminals;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.controllers.SimulationController;
import granicni_prelaz.javaprojekat2023.persons.Passenger;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.vehicles.Car;
import granicni_prelaz.javaprojekat2023.vehicles.Truck;
import granicni_prelaz.javaprojekat2023.vehicles.Vehicle;

import java.util.ArrayList;

public class CustomsTerminal extends Terminal{


    public CustomsTerminal(String terminalName , int position)
    {
        super(terminalName,  position);
    }
    @Override
    public void processVehicle() throws InterruptedException {

        Vehicle vehicle = this.vehicle;
        printInfo("Obrađuje vozilo: " + vehicle.getVehicleName());

        try
        {
            if(vehicle instanceof Car)
            {
                Thread.sleep(Constants.CUSTOMS_TERMINAL_WAIT);
            }
            else {
                for (Passenger passenger : new ArrayList<>(vehicle.getPassengers())) {
                    if(passenger.hasSuitcase() && passenger.getSuitcase().getHasIlligalThings())
                        ejectPassenger(passenger);
                }
            }
        }catch (Exception ex)        {
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
        Simulation.customsRecord.add(passenger.toString());
        System.out.println("Ejected passenger---- " + passenger.toString());

    }

    @Override
    void ejectVehicle() {

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
