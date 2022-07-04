package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer ferrari = new Manufacturer();
        ferrari.setName("Ferrari");
        ferrari.setCountry("Italy");
        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(ferrari);
        manufacturerDao.create(toyota);
        System.out.println(manufacturerDao.get(ferrari.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
        toyota.setCountry("Germany");
        System.out.println(manufacturerDao.update(toyota));
        System.out.println(manufacturerDao.delete(ferrari.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
