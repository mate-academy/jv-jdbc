package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final Manufacturer manufacturer = new Manufacturer();

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        manufacturer.setName("name");
        manufacturer.setCountry("country");

        manufacturerDao.create(manufacturer);

        Manufacturer manufacturerForUpdate = new Manufacturer();
        manufacturerForUpdate.setName("deleteName");
        manufacturerForUpdate.setCountry("deleteCountry");
        manufacturerForUpdate.setId(manufacturer.getId());

        manufacturerDao.update(manufacturerForUpdate);

        manufacturerDao.delete(manufacturerForUpdate.getId());

        System.out.println(manufacturerDao.get(40L));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
