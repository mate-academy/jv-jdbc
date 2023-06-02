package mate.jdbc.models;

import java.util.ArrayList;
import java.util.List;

public class ManufacturerGenerator {

    public List<Manufacturer> addManufacturers() {
        List<Manufacturer> manufacturers = new ArrayList<>();

        Manufacturer volvo = new Manufacturer();
        volvo.setName("Volvo");
        volvo.setCountry("Sweden");
        manufacturers.add(volvo);

        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");
        manufacturers.add(toyota);

        Manufacturer honda = new Manufacturer();
        honda.setName("Honda");
        honda.setCountry("Japan");
        manufacturers.add(honda);

        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("USA");
        manufacturers.add(ford);

        return manufacturers;
    }
}
