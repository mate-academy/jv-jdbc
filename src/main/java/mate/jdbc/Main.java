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
        System.out.println(manufacturerDao.get(9L));
        manufacturer.setName("Honda");
        manufacturer.setCountry("China");
        manufacturer = manufacturerDao.create(manufacturer);
        System.out.println("ADDED:"
                + manufacturerDao.get(manufacturer.getId()).orElseThrow());
        manufacturer.setCountry("Japan");
        manufacturer = manufacturerDao.update(manufacturer);
        System.out.println("UPDATED: "
                + manufacturerDao.get(manufacturer.getId()).orElseThrow());
        manufacturerDao.delete(1L);
        System.out.println("DELETED: "
                + manufacturerDao.get(manufacturer.getId()).orElseThrow());
    }
}
