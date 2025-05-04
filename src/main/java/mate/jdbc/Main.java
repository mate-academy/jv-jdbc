package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.create(toyota);
        System.out.println(manufacturerDao.get(5L));
        toyota.setName("LAnOS");
        manufacturerDao.update(toyota);
        System.out.println(toyota);
        manufacturerDao.delete(toyota.getId());
    }
}
