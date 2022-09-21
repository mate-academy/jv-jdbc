package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Application {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("Ukraine");
        manufacturer.setName("Fayni Lody");
        manufacturerDao.create(manufacturer);
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        Optional<Manufacturer> manufacturer1 = manufacturerDao.get(3L);
        manufacturerDao.delete(3L);
    }
}
