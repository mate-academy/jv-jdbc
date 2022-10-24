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
        Manufacturer kia = new Manufacturer("KIA", "Korean");
        Manufacturer skoda = new Manufacturer("Skoda", "Chech");
        Manufacturer daewoo = new Manufacturer("Daewoo", "Korean");
        Manufacturer manufacturer = manufacturerDao.create(kia);
        System.out.println(manufacturer);
        manufacturerDao.create(skoda);
        manufacturerDao.create(daewoo);
        System.out.println(manufacturerDao.getAll());
        Manufacturer ford = new Manufacturer(3L, "Ford", "USA");
        manufacturerDao.update(ford);
        System.out.println(manufacturerDao.getAll());
        Optional<Manufacturer> manufacturerWithIdOne = manufacturerDao.get(1L);
        System.out.println(manufacturerWithIdOne);
        System.out.println(manufacturerDao.delete(3L));
        System.out.println(manufacturerDao.delete(20L));
    }
}
