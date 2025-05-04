package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("NISSAN");
        manufacturer.setCountry("JAPAN");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("Lamborghini");
        manufacturer.setCountry("Italia");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("Jaguar");
        manufacturer.setCountry("Great Britain");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(3L));
        manufacturer.setId(3L);
        manufacturer.setCountry("International");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(1L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
