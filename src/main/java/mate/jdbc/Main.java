package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer("Peugeot", "France");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        for (Manufacturer allManufacturer : allManufacturers) {
            System.out.println(allManufacturer);
        }
        Manufacturer createManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(createManufacturer);
        Optional<Manufacturer> getManufacturer = manufacturerDao.get(manufacturer.getId());
        System.out.println("Get manufacturer by id: " + manufacturer.getId());
        Manufacturer updateManufacturer = new Manufacturer("ZAZ", "Ukraine");
        updateManufacturer.setId(manufacturer.getId());
        manufacturerDao.update(updateManufacturer);
        System.out.println("Updated manufacturer: " + updateManufacturer);
        System.out.println("All manufacturers:" + manufacturerDao.getAll());
        boolean deleteManufacturer = manufacturerDao.delete(manufacturer.getId());
        System.out.println("Deleted manufacturer: " + deleteManufacturer);
    }
}
