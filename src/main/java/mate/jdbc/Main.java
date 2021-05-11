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
        manufacturer.setName("name");
        manufacturer.setCountry("country");
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturer);

        Manufacturer manufacturerForUpdate = new Manufacturer();
        manufacturerForUpdate.setName("new name");
        manufacturerForUpdate.setCountry("new country");
        manufacturerForUpdate.setId(manufacturer.getId());

        manufacturerDao.update(manufacturerForUpdate);

        manufacturerDao.delete(manufacturerForUpdate.getId());

        System.out.println(manufacturerDao.get(1L));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
