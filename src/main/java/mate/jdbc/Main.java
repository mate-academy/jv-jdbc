package mate.jdbc;

import dao.Manufacturer;
import dao.ManufacturerDao;
import mate.jdbc.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                   .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Mercedes");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        System.out.println("getAll: " + manufacturerDao.getAll());
        System.out.println("get: " + manufacturerDao.get(manufacturer.getId()));
        manufacturer.setName("BMW");
        System.out.println("update: " + manufacturerDao.update(manufacturer));
        System.out.println("delete: " + manufacturerDao
                   .delete(manufacturer.getId()));
        manufacturerDao.create(manufacturer);
    }
}
