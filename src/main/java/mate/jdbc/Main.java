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
        manufacturer.setName("Toyota");
        manufacturer.setCountry("japan");
        Manufacturer createManufacturedDb = manufacturerDao.create(manufacturer);
        createManufacturedDb.setCountry("Japan");
        Manufacturer updateManufacturedDb = manufacturerDao.update(createManufacturedDb);
        Optional<Manufacturer> getManufacturedDb = manufacturerDao
                .get(updateManufacturedDb.getId());
        System.out.println(getManufacturedDb);
        manufacturerDao.delete(updateManufacturedDb.getId());
        List<Manufacturer> manufacturersList = manufacturerDao.getAll();
        for (Manufacturer manufacturerFromList : manufacturersList) {
            System.out.println(manufacturerFromList);
        }
    }
}
