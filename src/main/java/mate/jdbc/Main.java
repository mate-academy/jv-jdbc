package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer honda = new Manufacturer();
        honda.setName("honda");
        honda.setCountry("Japan");
        Manufacturer volkswagen = new Manufacturer();
        volkswagen.setName("volkswagen");
        volkswagen.setCountry("Germany");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.create(honda));
        System.out.println(manufacturerDao.create(volkswagen));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(1L));
        honda.setCountry("Ukraine");
        System.out.println(manufacturerDao.update(honda));
        System.out.println(manufacturerDao.delete(1L));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(5L));
    }
}
