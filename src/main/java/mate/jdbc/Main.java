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
        Manufacturer mercedes = new Manufacturer("Mercedes", "Germany");
        manufacturerDao.create(mercedes);
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        manufacturerDao.create(toyota);
        Manufacturer hyundai = new Manufacturer("Hyundai", "Korea");
        manufacturerDao.create(hyundai);
        Manufacturer manufacturerFromDb = manufacturerDao.get(2L).get();
        System.out.println(manufacturerFromDb);
        manufacturerFromDb.setName("Audi");
        manufacturerFromDb.setCountry("Germany");
        manufacturerDao.update(manufacturerFromDb);
        manufacturerDao.delete(3L);
        List<Manufacturer> manufacturersList = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturersList) {
            System.out.println(manufacturer);
        }
    }
}
