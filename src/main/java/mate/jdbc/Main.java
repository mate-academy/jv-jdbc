package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer audi = new Manufacturer("audi", "Germany");
        Manufacturer ford = new Manufacturer("ford", "USA");
        Manufacturer toyota = new Manufacturer("toyota", "Japan");
        manufacturerDao.create(audi);
        manufacturerDao.create(ford);
        manufacturerDao.create(toyota);
        Optional<Manufacturer> manufacturer = manufacturerDao.get(2L);
        manufacturerDao.delete(2L);
        manufacturerDao.update(audi);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
