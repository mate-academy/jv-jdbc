package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("bmw");
        manufacturer.setCountry("germany");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(manufacturer);
        System.out.println("getAll: " + manufacturerDao.getAll());
        System.out.println("get: " + manufacturerDao.get(manufacturer.getId()));
        manufacturer.setName("audi");
        System.out.println("update: " + manufacturerDao.update(manufacturer));
        System.out.println("delete: " + manufacturerDao.delete(manufacturer.getId()));
    }
}
