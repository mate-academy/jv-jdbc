package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        final ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();

        System.out.println("Create: ");
        manufacturer.setName("Mercedes-Benz");
        manufacturer.setCountry("Germany");
        Manufacturer mercedes = manufacturerDao.create(manufacturer);
        System.out.println(mercedes);

        System.out.println("Get manufacturer: ");
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(mercedes.getId());
        System.out.println(optionalManufacturer);

        System.out.println("Get all manufacturers: ");
        System.out.println(manufacturerDao.getAll());

        System.out.println("Update manufacturer: ");
        manufacturer.setName("Pontiac");
        System.out.println(manufacturerDao.update(manufacturer));

        System.out.println("Delete manufacturer: ");
        boolean deleteManufacturer = manufacturerDao.delete(manufacturer.getId());
        System.out.println(deleteManufacturer);

    }
}
