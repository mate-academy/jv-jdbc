package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerForCreate = new Manufacturer();
        manufacturerForCreate.setName("Bob");
        manufacturerForCreate.setCountry("USA");
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturerForCreate);
        System.out.println(savedManufacturer);

        Manufacturer manufacturerForUpdate = new Manufacturer();
        manufacturerForUpdate.setId(savedManufacturer.getId());
        manufacturerForUpdate.setName("Alisa");
        manufacturerForUpdate.setCountry("France");
        Manufacturer update = manufacturerDao.update(manufacturerForUpdate);
        System.out.println(update);

        System.out.println(manufacturerDao.get(4L));

        manufacturerDao.delete(savedManufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
