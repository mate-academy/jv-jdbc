package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {

        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Toyota");
        manufacturer1.setCountry("Japan");

        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("BMW");
        manufacturer2.setCountry("Germany");

        Manufacturer manufacturer3 = new Manufacturer();
        manufacturer3.setName("KIA");
        manufacturer3.setCountry("South Korea");

        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);

        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        manufacturerDao.create(manufacturer3);

        System.out.println("Test get: " + manufacturerDao.get(manufacturer2.getId()));

        Manufacturer manufacturerForUpdateTest = new Manufacturer();
        manufacturerForUpdateTest.setId(manufacturer3.getId());
        manufacturerForUpdateTest.setName("SEAT");
        manufacturerForUpdateTest.setCountry("Spain");
        System.out.println(manufacturerDao.update(manufacturerForUpdateTest));

        manufacturerDao.delete(manufacturer1.getId());

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
    }
}
