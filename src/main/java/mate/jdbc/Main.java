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
        manufacturer.setName("Samsung");
        manufacturer.setCountry("Seoul");
        Manufacturer manufacturerUpdated = manufacturerDao.create(manufacturer);
        manufacturerUpdated.setCountry("Suwon");
        Manufacturer updateManufacturer = manufacturerDao.update(manufacturerUpdated);
        Optional<Manufacturer> getManufacturedDb = manufacturerDao
                .get(updateManufacturer.getId());
        System.out.println(getManufacturedDb);
        manufacturerDao.delete(updateManufacturer.getId());
        List<Manufacturer> manufacturersList = manufacturerDao.getAll();
        for (Manufacturer manufacturerFromList : manufacturersList) {
            System.out.println(manufacturerFromList);
        }
    }
}
