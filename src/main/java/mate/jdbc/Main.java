package mate.jdbc;

import mate.jdbc.dao.manufacturer.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("volkswagen");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturerDao.get(manufacturer.getId()));
        System.out.println(manufacturerDao.getAll());
        manufacturer.setName("Germany");
        System.out.println(manufacturerDao.update(manufacturer));
        manufacturerDao.delete(manufacturer.getId());
    }
}
