package granicni_prelaz.javaprojekat2023.map;

import granicni_prelaz.javaprojekat2023.vehicles.Vehicle;

public class Field {
    private final int xPosition;
    private final int yPosition;



    private boolean hasVehicle;
    private Vehicle vehicle;


    public Field(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        this.hasVehicle = false;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }


    public synchronized  boolean isHasVehicle() {
        return hasVehicle;
    }

    public void setHasVehicle(boolean hasVehicle) {
        this.hasVehicle = hasVehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            this.vehicle = null;
            this.hasVehicle = false;
        } else {
            this.vehicle = vehicle;
            this.setHasVehicle(true);
        }
    }
}
