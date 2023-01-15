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
        manufacturer.setName("Volvo");
        manufacturer.setCountry("China");
        System.out.println(manufacturerDao.create(manufacturer));
        System.out.println(manufacturerDao.get(3L));
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.delete(8L));
        System.out.println(manufacturerDao.getAll());
        Manufacturer manufacturerUpdate = new Manufacturer();
        manufacturerUpdate.setId(10L);
        manufacturerUpdate.setName("Seat");
        manufacturerUpdate.setCountry("France");
        System.out.println(manufacturerDao.update(manufacturerUpdate));
        System.out.println(manufacturerDao.getAll());
    }
}
