package granicni_prelaz.javaprojekat2023.incident;

import granicni_prelaz.javaprojekat2023.persons.Passenger;
import granicni_prelaz.javaprojekat2023.persons.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListOfPunishedPersons implements Serializable {

    List<Person> persons = new ArrayList<>();

    public void addPerson(Person person)
    {
        persons.add(person);
    }

    public List<Person> getPersons() {
        return persons;
    }

    public String toString() {
        StringBuilder list = new StringBuilder();
        for(Person p: persons) {
            list.append(p.toString()).append(", Ime vozila: ").append(p.getVehicleName()).append("\n");
        }
        return list.toString();
    }
}
