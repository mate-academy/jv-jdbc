package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        toyota = manufacturerDao.create(toyota);
        bmw = manufacturerDao.create(bmw);
        System.out.println("Create 2 manufacturers");

        manufacturerDao.getAll().forEach(System.out::println);

        manufacturerDao.delete(bmw.getId());
        System.out.println("Delete BMW manufacturer");

        manufacturerDao.getAll().forEach(System.out::println);

        toyota.setCountry("Korea");
        manufacturerDao.update(toyota);
        System.out.println("Update Toyota manufacturer");
        System.out.println(manufacturerDao.get(toyota.getId()));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
