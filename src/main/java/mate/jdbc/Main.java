package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer tesla = new Manufacturer();
        tesla.setName("Tesla");
        tesla.setCountry("USA");
        manufacturerDao.create(tesla);

        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("Canada");
        manufacturerDao.create(ford);

        System.out.println(manufacturerDao.get(tesla.getId()));
        System.out.println(manufacturerDao.getAll());
        ford.setName("Ford");
        manufacturerDao.update(ford);
        System.out.println(manufacturerDao.get(ford.getId()));
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(ford.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
