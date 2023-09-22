package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturerFirst = new Manufacturer();
        manufacturerFirst.setName("First");
        manufacturerFirst.setCountry("First");

        Manufacturer manufacturerSecond = new Manufacturer();
        manufacturerSecond.setName("Second");
        manufacturerSecond.setCountry("Second");

        Manufacturer manufacturerThird = new Manufacturer();
        manufacturerThird.setName("Third");
        manufacturerThird.setCountry("Third");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerFirst = manufacturerDao.create(manufacturerFirst);
        manufacturerSecond = manufacturerDao.create(manufacturerSecond);
        manufacturerThird = manufacturerDao.create(manufacturerThird);

        System.out.println(manufacturerDao.get(4L).orElse(manufacturerThird));

        System.out.println(manufacturerDao.getAll());

        manufacturerSecond.setName("newSecond");
        manufacturerSecond.setCountry("newSecond");
        System.out.println(manufacturerDao.update(manufacturerSecond));

        manufacturerDao.delete(6L);
    }
}
