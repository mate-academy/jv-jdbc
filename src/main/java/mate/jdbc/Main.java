package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("BMW", "Korea");
        manufacturerDao.create(manufacturer);
        manufacturer = manufacturerDao.create(manufacturer);
        manufacturerDao.get(manufacturer.getId());
        manufacturerDao.getAll();
        manufacturer.setCountry("Germany");
        manufacturerDao.update(manufacturer);
        System.out.println(manufacturer);
        manufacturerDao.delete(manufacturer.getId());

    }
}
