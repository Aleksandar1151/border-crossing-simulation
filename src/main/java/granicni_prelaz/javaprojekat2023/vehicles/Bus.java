package granicni_prelaz.javaprojekat2023.vehicles;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.gadgets.Suitcase;

import java.util.List;
import java.util.Random;

public class Bus extends Vehicle {
    public static int ID_Counter = 0;
    int id;
    public List<Suitcase> passengerSuitcases;


    public Bus() {
        super("B " + ID_Counter, Constants.MAX_BUS_PASSENGER);
        id = ID_Counter++;
        createSuitcases();

    }

    private void createSuitcases() {
        Random rnd = new Random();
        for (int i = 0; i < passengers.size(); i++)
            if (rnd.nextInt(100) > 30) {
                passengers.get(i).setSuitcase(new Suitcase(passengers.get(i).getId()));
            }
    }

    void kreirajPutnike() {
        Random rnd = new Random();
        if (rnd.nextInt(100) > 30) {
            //kofer = new Kofer();
        }

    }
}
