package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer1 = new Manufacturer(1L, "FORD", "USA");
        Manufacturer manufacturer2 = new Manufacturer(2L, "NISSAN", "JAPAN");
        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(2L));
        Manufacturer manufacturer3 = new Manufacturer(2L, "BMW", "GERMANY");
        System.out.println(manufacturerDao.update(manufacturer3));
        manufacturerDao.delete(1L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
