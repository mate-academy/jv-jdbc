package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer lambo = new Manufacturer("Lamborghini", "Italy");
        Manufacturer mercedes = new Manufacturer("Mercedes-Benz", "Germany");
        Manufacturer zaz = new Manufacturer("zaz", "Ukraine");
        manufacturerDao.create(lambo);
        manufacturerDao.create(mercedes);
        manufacturerDao.create(zaz);
        manufacturerDao.get(1L);
        manufacturerDao.delete(2L);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
    }
}
