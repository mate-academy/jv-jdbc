package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao = (ManufacturerDao) injector
            .getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Fiat");
        manufacturer.setCountry("France");
        Manufacturer createdManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(createdManufacturer);
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(4L);
        if (optionalManufacturer.isPresent()) {
            manufacturer = optionalManufacturer.get();
            manufacturer.setName("Renault");
            manufacturer.setCountry("France");
            Manufacturer updatedManufacturer = manufacturerDao.update(manufacturer);
            System.out.println(updatedManufacturer);
        }
        manufacturerDao.delete(1L);
        manufacturerDao.getAll();
    }
}
