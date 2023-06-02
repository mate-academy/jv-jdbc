package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer tesla = new Manufacturer();
        tesla.setName("tesla");
        tesla.setCountry("usa");

        manufacturerDao.create(tesla);
        tesla.setName("tesla model T");
        tesla.setCountry("united states of America");
        manufacturerDao.update(tesla);
        System.out.println(manufacturerDao.get(tesla.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("delete successful: " + manufacturerDao.delete(tesla.getId()));
    }
}
