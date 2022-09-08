package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Aston Martin");
        manufacturer.setCountry("United Kingdom");

        Manufacturer createManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(createManufacturer);

        Optional<Manufacturer> requiredManufacturer = manufacturerDao
                .get(createManufacturer.getId());
        System.out.println(requiredManufacturer.get());

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer newManufacturer : manufacturers) {
            System.out.println(newManufacturer);
        }
        manufacturer.setCountry("England");
        manufacturer = manufacturerDao.update(manufacturer);
        System.out.println(manufacturer);

        manufacturerDao.delete(manufacturer.getId());
        manufacturers = manufacturerDao.getAll();
        for (Manufacturer newManufacturer : manufacturers) {
            System.out.println(newManufacturer);
        }
    }
}
