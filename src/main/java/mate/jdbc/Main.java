package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Opel");
        manufacturer.setCountry("Germany");
        Manufacturer manufacturerWithId = manufacturerDao.create(manufacturer);
        manufacturerWithId.setName("Fiat");
        manufacturerWithId.setCountry("Italy");
        manufacturerDao.update(manufacturerWithId);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        Manufacturer manufacturerById = manufacturerDao.get(
                manufacturerWithId.getId()).orElseThrow(() -> new RuntimeException(
                "Can't get manufacturer by id " + manufacturerWithId.getId()));
        if (!manufacturerDao.delete(manufacturerWithId.getId())) {
            throw new RuntimeException("Can't delete manufacturer by id "
                    + manufacturerWithId.getId());
        }
    }
}
