package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) INJECTOR.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerToCreate = new Manufacturer();
        manufacturerToCreate.setName("BMW");
        manufacturerToCreate.setCountry("Germany");
        Manufacturer createdManufacturer = manufacturerDao.create(manufacturerToCreate);

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();

        Optional<Manufacturer> manufacturer = manufacturerDao.get(2L);

        manufacturerToCreate.setCountry("Austria");
        Manufacturer updatedManufacturer = manufacturerDao.update(createdManufacturer);

        boolean res = manufacturerDao.delete(1L);
    }
}
