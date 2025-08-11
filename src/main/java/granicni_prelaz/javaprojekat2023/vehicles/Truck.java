package granicni_prelaz.javaprojekat2023.vehicles;

import granicni_prelaz.javaprojekat2023.constants.Constants;
import granicni_prelaz.javaprojekat2023.gadgets.CustomsDocumentation;

import java.util.Random;

public class Truck extends Vehicle{
    public static int ID_Counter = 0;
    int id;
    CustomsDocumentation customsDocumentation;
    double realWeight;

    public Truck()
    {
        super(("K "+ ID_Counter), Constants.MAX_TRUCK_PASSENGER);
        id = ID_Counter++;
        Random rnd = new Random();
        if(rnd.nextInt(100)>50)
        {
            double declaredWeight = 50 + rnd.nextDouble(50);
            double factor = 1.0;
            if(rnd.nextInt(100)<20)
                factor = 1.0 + (rnd.nextDouble()*0.3);
            realWeight = declaredWeight * factor;
            customsDocumentation = new CustomsDocumentation(declaredWeight);
        }
    }

    public double getRealWeight() {
        return realWeight;
    }

    public CustomsDocumentation getCustomsDocumentation() {
        return customsDocumentation;
    }

     public boolean hasCustomDocumentation()
    {
        return customsDocumentation != null;
    }
}
