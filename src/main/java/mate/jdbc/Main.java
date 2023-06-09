package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc.dao");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer createManufacturer = new Manufacturer();
        createManufacturer.setName("Nestle");
        createManufacturer.setCountry("Switzerland");
        manufacturerDao.create(createManufacturer);
        manufacturerDao.get(1L);
        manufacturerDao.getAll();
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(8L);
        Manufacturer updateManufacturer = optionalManufacturer.get();
        updateManufacturer.setName("Update manufacturer");
        updateManufacturer.setCountry("Update country");
        manufacturerDao.update(updateManufacturer);
        manufacturerDao.delete(9L);
    }
}
