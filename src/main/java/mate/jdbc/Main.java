package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(7L);
        manufacturer.setName("Honda");
        manufacturer.setCountry("Japan");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.update(manufacturer);
        System.out.println(manufacturerDao.get(8L));
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
        manufacturerDao.create(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
