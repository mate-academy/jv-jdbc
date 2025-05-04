package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer volkswagen = new Manufacturer();
        volkswagen.setName("Volkswagen");
        volkswagen.setCountry("Germany");
        manufacturerDao.create(volkswagen);

        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");
        manufacturerDao.create(toyota);

        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.getAll());
        toyota.setName("toyota");
        manufacturerDao.update(toyota);
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(2L);
        System.out.println(manufacturerDao.getAll());
    }
}
