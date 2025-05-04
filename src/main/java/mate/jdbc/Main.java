package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Hyundai");
        manufacturer.setCountry("South Korea");
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturer);
        System.out.println("New manufacturer has been created: " + savedManufacturer);

        Long id = 6L;
        Optional<Manufacturer> manufacturerOpt = manufacturerDao.get(id);
        if (manufacturerOpt.isPresent()) {
            System.out.println("Manufacturer was received from DB: " + manufacturerOpt.get());
        } else {
            System.out.println("Manufacturer with id " + id + " was not found");
        }

        manufacturer.setId(6L);
        manufacturer.setName("Peugeot");
        manufacturer.setCountry("France");
        Manufacturer updatedManufacturer = manufacturerDao.update(manufacturer);
        System.out.println("Manufacturer has been changed: " + updatedManufacturer);

        id = 6L;
        if (manufacturerDao.delete(id)) {
            System.out.println("Manufacturer with id " + id + " was successfully deleted from DB");
        }

        List<Manufacturer> manufacturersList = manufacturerDao.getAll();
        System.out.println("Full list of manufacturers:");
        manufacturersList.forEach(System.out::println);
    }
}
