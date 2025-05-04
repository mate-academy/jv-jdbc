package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        List<Manufacturer> manufacturers = List.of(
                new Manufacturer().setName("Mazda").setCountry("Japan"),
                new Manufacturer().setName("Ford").setCountry("USA"),
                new Manufacturer().setName("Mercedes").setCountry("Germany"));
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturers.forEach(manufacturerDao::create);
        Manufacturer manufacturerFromDb = manufacturerDao.get(manufacturers.get(0).getId())
                .orElseGet(Manufacturer::new);
        manufacturerFromDb.setName("Lexus");
        manufacturerDao.update(manufacturerFromDb);
        manufacturerDao.delete(manufacturerFromDb.getId());
        List<Manufacturer> manufacturersFromDb = manufacturerDao.getAll();
        manufacturersFromDb.forEach(m -> logger.info(m.toString()));
    }
}
