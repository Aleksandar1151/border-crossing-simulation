package granicni_prelaz.javaprojekat2023.terminals;

import granicni_prelaz.javaprojekat2023.persons.Passenger;
import granicni_prelaz.javaprojekat2023.vehicles.Vehicle;

public abstract class Terminal {

    String terminalName;
    boolean isBusy;
    boolean isInFunction;

    Vehicle vehicle;

    int position;

    public abstract void processVehicle() throws InterruptedException;
    abstract void ejectPassenger(Passenger passenger);

    abstract void ejectVehicle();

    public abstract boolean acceptVehicle(Vehicle vehicleType);


    public Terminal(String terminalName, int position)
    {
        this.terminalName = terminalName;
        this.position = position;
        this.isBusy = false;
        this.isInFunction = true;
        this.vehicle = null;
    }



    public void setInFunction(boolean inFunction) {
        this.isInFunction = inFunction;
    }

    public void setBusy(boolean busy) {
        this.isBusy = busy;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public boolean hasVehicle()
    {
        return (vehicle == null) ? false : true;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isInFunction() {
        return isInFunction;
    }

    protected boolean checkIfBusy()
    {
        return vehicle == null;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public abstract void printInfo(String text);
}
