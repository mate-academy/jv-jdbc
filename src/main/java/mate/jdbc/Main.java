package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer skoda = new Manufacturer("Skoda", "Czech Republic");
        Manufacturer zazTavria = new Manufacturer("ZAZ - Tavria", "Ukraine");
        Manufacturer ford = new Manufacturer("Ford", "USA");
        manufacturerDao.create(skoda);
        manufacturerDao.create(zazTavria);
        manufacturerDao.create(ford);
        Optional<Manufacturer> manufacturer = manufacturerDao.get(2L);
        manufacturerDao.delete(2L);
        manufacturerDao.update(skoda);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
