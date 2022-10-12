package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Audi");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);

        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(1L));

        manufacturer.setName("Volvo");
        manufacturer.setCountry("Sweden");
        manufacturer.setId(3L);
        manufacturerDao.update(manufacturer);

        manufacturerDao.delete(5L);
        manufacturerDao.getAll().forEach(System.out::println);

    }
}
