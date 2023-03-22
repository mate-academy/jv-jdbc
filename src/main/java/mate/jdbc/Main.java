package mate.jdbc;

import mate.jdbc.dao.Injector;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        // CREATE
        Manufacturer manufacturerFromGermany = new Manufacturer();
        manufacturerFromGermany.setName("name_1");
        manufacturerFromGermany.setCountry("Germany");
        manufacturerDao.create(manufacturerFromGermany);
        Manufacturer manufacturerFromUkraine = new Manufacturer();
        manufacturerFromUkraine.setName("name_2");
        manufacturerFromUkraine.setCountry("Ukraine");
        manufacturerDao.create(manufacturerFromUkraine);
        Manufacturer manufacturerFromItaly = new Manufacturer();
        manufacturerFromItaly.setName("name_3");
        manufacturerFromItaly.setCountry("Italy");
        manufacturerDao.create(manufacturerFromItaly);
        // PRINT ALL
        System.out.println(manufacturerDao.getAll());
        // PRINT ONE MANUFACTURER
        System.out.println(manufacturerDao.get(manufacturerFromGermany.getId()));
        // UPDATE
        manufacturerFromGermany.setCountry("updateCountry");
        manufacturerDao.update(manufacturerFromGermany);
        // DELETE
        manufacturerDao.delete(manufacturerFromItaly.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
