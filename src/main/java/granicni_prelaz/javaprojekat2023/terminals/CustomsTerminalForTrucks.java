package granicni_prelaz.javaprojekat2023.terminals;

import granicni_prelaz.javaprojekat2023.persons.Passenger;
import granicni_prelaz.javaprojekat2023.simulation.Simulation;
import granicni_prelaz.javaprojekat2023.vehicles.Truck;
import granicni_prelaz.javaprojekat2023.vehicles.Vehicle;

import java.util.ArrayList;

public class CustomsTerminalForTrucks extends CustomsTerminal {

    public CustomsTerminalForTrucks(String terminalName, int position)
    {
        super(terminalName, position);
    }

    @Override
    public boolean acceptVehicle(Vehicle vehicleType) {
        return (vehicleType instanceof Truck) ? true : false;
    }
    @Override
    public void processVehicle()
    {
        Vehicle vehicle = this.vehicle;
        printInfo("Obrađuje vozilo: " + vehicle.getVehicleName());
        Truck truck = (Truck) vehicle;
        if(truck.hasCustomDocumentation() && truck.getRealWeight() > truck.getCustomsDocumentation().getDeclaredWeight())
        {
            ejectVehicle();
        }
        printInfo("Završena obrada vozila: " + vehicle.getVehicleName());

        try {
            vehicle.sleep(2000);
        } catch (InterruptedException e) {
           // throw new RuntimeException(e);
        }
    }

    void ejectVehicle() {

        vehicle.setEjected(true);
        Simulation.customsRecord.add(vehicle.getDriver().toString() + " je izbacen jer nema ispravnu carinsku dokumentaciju.");
        for (Passenger passenger: new ArrayList<>(vehicle.getPassengers()) ) {
            ejectPassenger(passenger);
        }
        setVehicle(null);

        if(Simulation.queueVehicles.peek() == vehicle)
            Simulation.queueVehicles.remove();

    }

}
