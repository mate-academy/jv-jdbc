package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerOne = new Manufacturer();
        manufacturerOne.setName("manufacturerOne");
        manufacturerOne.setCountry("Germany");
        System.out.println(manufacturerDao.create(manufacturerOne));
        Manufacturer manufacturerTwo = new Manufacturer();
        manufacturerOne.setName("manufacturerTwo");
        manufacturerOne.setCountry("France");
        System.out.println(manufacturerDao.create(manufacturerTwo));
        System.out.println(manufacturerDao.create(manufacturerOne));
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerTwo.setCountry("Italy");
        System.out.println(manufacturerDao.update(manufacturerTwo));
        System.out.println(manufacturerDao.delete(1L));
        System.out.println(manufacturerDao.delete(33L));
        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(3L));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
