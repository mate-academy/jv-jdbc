package mate.jdbc;

import java.util.List;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.dao.ManufacturerDao;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        List<Manufacturer> manufacturers = List.of(
                new Manufacturer("Alfa", "Argentina"),
                new Manufacturer("Beta", "Belgium"),
                new Manufacturer("Delta", "Denmark"));

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        manufacturers.forEach(manufacturerDao::create);
        Manufacturer manufacturerFromDb = manufacturerDao
                .get(manufacturers.get(0).getId())
                .orElseGet(Manufacturer::new);
        manufacturerFromDb.setName("Gamma");
        manufacturerDao.update(manufacturerFromDb);
        manufacturerDao.delete(manufacturerFromDb.getId());
    }
}
