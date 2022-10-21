package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import mate.jdbc.models.Manufacturer;

public class TestMethod {
    private List<Manufacturer> manufacturerList = new ArrayList<>();

    public List<Manufacturer> createManufacturers() {
        for (int i = 0; i < 10; i++) {
            Manufacturer createManufacturer = new Manufacturer();
            createManufacturer.setName("Dynamic Grid " + i);
            createManufacturer.setCountry("USA " + i);
            manufacturerList.add(createManufacturer);
        }
        return manufacturerList;
    }
}
