package granicni_prelaz.javaprojekat2023.map;

import granicni_prelaz.javaprojekat2023.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class PathOnMap {
    private final List<Field> pathFields;

    public PathOnMap() {
        pathFields = new ArrayList<>();
    }

    public void addPathField(Field field) {
        pathFields.add(field);
    }

    public List<Field> getPathFields() {
        return pathFields;
    }

    public void setVehicleOnPosition(Vehicle vehicle, int position)
    {
        Field field;

        field = pathFields.get(position+1);
        field.setVehicle(null);

        field = pathFields.get(position);
        field.setVehicle(vehicle);
    }

}
