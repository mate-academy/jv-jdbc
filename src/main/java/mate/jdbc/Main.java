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

        manufacturer.setCountry("Germany");
        manufacturer.setName("Mazda");
        manufacturer = manufacturerDao.create(manufacturer);

        System.out.println(manufacturerDao.get(manufacturer.getId()).get());

        manufacturer.setCountry("France");
        manufacturer.setName("Reno");
        System.out.println(manufacturerDao.update(manufacturer).toString());

        manufacturerDao.getAll().forEach(System.out::println);

        manufacturerDao.delete(manufacturer.getId());

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
