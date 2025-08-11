package granicni_prelaz.javaprojekat2023.persons;

public class Driver extends Person {

    public Driver(String vehicleName)
    {
        super(vehicleName);
    }


    public String toString()
    {
        return "-- V [" + vehicleName +"] " + super.toString();
    }

}
