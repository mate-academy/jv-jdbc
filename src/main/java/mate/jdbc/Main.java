package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> all = manufacturerDao.getAll();
        Optional<Manufacturer> manufacturer = manufacturerDao.get(2L);
        Manufacturer ford = new Manufacturer("Ford", "America", 10L);
        manufacturerDao.update(ford);
    }
}
