package granicni_prelaz.javaprojekat2023.vehicles;

import granicni_prelaz.javaprojekat2023.constants.Constants;

public class Car extends Vehicle {
    public static int ID_Counter = 0;
    int id;
    public Car() {
        super("A " + ID_Counter, Constants.MAX_CAR_PASSENGER);
        id = ID_Counter++;
    }

}
