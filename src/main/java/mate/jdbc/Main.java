package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {

        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");

        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");

        Manufacturer kia = new Manufacturer();
        kia.setName("KIA");
        kia.setCountry("South Korea");

        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);

        manufacturerDao.create(toyota);
        manufacturerDao.create(bmw);
        manufacturerDao.create(kia);

        System.out.println("Test get: " + manufacturerDao.get(bmw.getId()));

        Manufacturer manufacturerForUpdateTest = new Manufacturer();
        manufacturerForUpdateTest.setId(kia.getId());
        manufacturerForUpdateTest.setName("SEAT");
        manufacturerForUpdateTest.setCountry("Spain");
        System.out.println(manufacturerDao.update(manufacturerForUpdateTest));

        manufacturerDao.delete(toyota.getId());

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
    }
}
