package granicni_prelaz.javaprojekat2023.persons;

import granicni_prelaz.javaprojekat2023.gadgets.IdentificationDocument;
import granicni_prelaz.javaprojekat2023.gadgets.Suitcase;

import java.io.Serializable;
import java.util.Random;

public abstract class Person implements Serializable {

    public static int ID_Counter = 1;
    int id;
    Suitcase suitcase;
    IdentificationDocument identificationDocument;
    String vehicleName;
    Person(String vehicleName)
    {
        Random rnd = new Random();
        identificationDocument = new IdentificationDocument(ID_Counter);
        id = ID_Counter++;
        this.vehicleName = vehicleName;
    }

    public int getId() {
        return id;
    }

    public void setSuitcase(Suitcase suitcase) {
        this.suitcase = suitcase;
    }

    public IdentificationDocument getIdentificationDocument() {
        return identificationDocument;
    }

    public Suitcase getSuitcase() {
        return suitcase;
    }

    public boolean hasSuitcase()
    {
        return suitcase != null;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String toString()
    {
        return "P"+id+" " + identificationDocument + ((suitcase == null ) ? "" : suitcase) ;
    }
}
