package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerBmw = new Manufacturer("BMW", "Germany");
        manufacturerDao.create(manufacturerBmw);
        Manufacturer manufacturerKia = new Manufacturer("KIA", "South Korea");
        manufacturerDao.create(manufacturerKia);
        Long id = 2L;
        System.out.println(manufacturerDao.get(id));
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }
        manufacturerBmw.setCountry("China");
        manufacturerDao.update(manufacturerBmw);
        manufacturerDao.delete(id);
    }
}
