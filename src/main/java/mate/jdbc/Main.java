package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {

    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        //Create
        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setName("Mercedes");
        newManufacturer.setCountry("Germany");
        manufacturerDao.create(newManufacturer);
        //Read
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }
        Long id = 2L;
        Optional<Manufacturer> manufacturer = manufacturerDao.get(id);
        if (!manufacturer.isEmpty()) {
            System.out.println(manufacturer.get());
        } else {
            System.out.println("There is no manufacturer with such id (id = " + id + ")");
        }
        //Update
        newManufacturer.setName("Mercedes2");
        manufacturerDao.update(newManufacturer);
        //Delete
        manufacturerDao.delete(newManufacturer.getId());

    }
}
