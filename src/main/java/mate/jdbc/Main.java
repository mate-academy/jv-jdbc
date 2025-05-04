package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer fiat = new Manufacturer("fiat", "Italy");
        Manufacturer honda = new Manufacturer("honda", "Japan");
        Manufacturer ford = new Manufacturer("ford", "USA");
        manufacturerDao.create(fiat);
        manufacturerDao.create(honda);
        manufacturerDao.create(ford);
        manufacturerDao.delete(honda.getId());
        honda.setCountry("Korea");
        honda.setName("hyundai");
        manufacturerDao.update(honda);
        manufacturerDao.get(8L);
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }
    }
}
