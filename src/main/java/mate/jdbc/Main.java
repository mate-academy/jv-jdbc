package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.jdbc");
    private static ManufacturerDao manufacturerDao;

    public static void main(String[] args) {
        manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        getAllManufacturers();
        System.out.println("- - - - - - - - - - - - - - - - - - -");
        System.out.println("Manufacturer with id 2: " + manufacturerDao.get(2L));
        System.out.println("Manufacturer with id 4: " + manufacturerDao.get(4L));
        System.out.println("- - - - - - - - - - - - - - - - - - -");
        createManufacturer("Mazda", "Japan");
        createManufacturer("Audi", "Germany");
        System.out.println("Some manufacturers were added:" + System.lineSeparator());
        getAllManufacturers();
        System.out.println("- - - - - - - - - - - - - - - - - - -");
        System.out.println(manufacturerDao.delete(3L));
        System.out.println(manufacturerDao.delete(5L));
        System.out.println("- - - - - - - - - - - - - - - - - - -");
        System.out.println("Some manufacturers were deleted:" + System.lineSeparator());
        getAllManufacturers();
        System.out.println("- - - - - - - - - - - - - - - - - - -");
        System.out.println("Manufacturer with id 1 before changes: " + manufacturerDao.get(1L));
        updateManufacturer(1L, "Honda", "Japan");
        System.out.println("Manufacturer with id 1 after changes: " + manufacturerDao.get(1L));
        System.out.println("Manufacturer with id 2 before changes: " + manufacturerDao.get(2L));
        updateManufacturer(2L, "Volvo", "Sweden");
        System.out.println("Manufacturer with id 2 after changes: " + manufacturerDao.get(2L));
        System.out.println("- - - - - - - - - - - - - - - - - - -");
        getAllManufacturers();
    }

    private static void createManufacturer(String name, String country) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        manufacturerDao.create(manufacturer);
    }

    private static void getAllManufacturers() {
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }
    }

    private static void updateManufacturer(Long id, String name, String country) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        manufacturerDao.update(manufacturer);
    }
}
