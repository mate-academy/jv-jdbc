package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer tesla = manufacturerDao
                .create(new Manufacturer("Tesla", "USA"));
        Manufacturer bmw = manufacturerDao
                .create(new Manufacturer("BMW", "Germany"));
        Manufacturer nissan = manufacturerDao
                .create(new Manufacturer("Nissan", "Japan"));
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.delete(nissan.getId()));
        tesla.setCountry("North America");
        manufacturerDao.update(tesla);
        System.out.println(manufacturerDao.get(tesla.getId()));
    }
}


