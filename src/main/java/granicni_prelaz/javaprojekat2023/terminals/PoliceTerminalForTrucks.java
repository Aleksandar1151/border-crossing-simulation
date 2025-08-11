package granicni_prelaz.javaprojekat2023.terminals;

import granicni_prelaz.javaprojekat2023.vehicles.Truck;
import granicni_prelaz.javaprojekat2023.vehicles.Vehicle;

public class PoliceTerminalForTrucks extends PoliceTerminal {

    public PoliceTerminalForTrucks(String terminalName, int position)
    {
        super(terminalName,  position);
    }

    @Override
    public boolean acceptVehicle(Vehicle vehicleType) {
        return (vehicleType instanceof Truck) ? true : false;
    }
}
