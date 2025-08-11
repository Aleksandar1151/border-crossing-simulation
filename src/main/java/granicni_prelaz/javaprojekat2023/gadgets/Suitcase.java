package granicni_prelaz.javaprojekat2023.gadgets;

import java.io.Serializable;
import java.util.Random;

public class Suitcase implements Serializable {

    int personID;
    boolean hasIlligalThings;

    public Suitcase(int personID)
    {
        this.personID = personID;
        Random rnd = new Random();

        if(rnd.nextInt(100) < 10)
        {
            hasIlligalThings = true;
        }
    }

    public boolean getHasIlligalThings()
    {
        return hasIlligalThings;
    }

    public String toString()
    {
        return "Kofer " + (hasIlligalThings ? " ima" : " nema") + " nedozvoljene stvari." ;
    }
}
