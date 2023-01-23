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
        manufacturerOne.setCountry("Ukraine");
        manufacturerOne.setName("Stepan");
        manufacturerDao.create(manufacturerOne);

        manufacturerDao.get(manufacturerOne.getId());

        Manufacturer manufacturerTwo = new Manufacturer();
        manufacturerTwo.setName("Bill");
        manufacturerTwo.setCountry("USA");
        manufacturerDao.create(manufacturerTwo);

        manufacturerTwo.setCountry("France");
        manufacturerDao.update(manufacturerTwo);

        manufacturerDao.delete(manufacturerTwo.getId());

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
