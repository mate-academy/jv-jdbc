package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        // initialize field values using setters or constructor
        Manufacturer manufacturer = new Manufacturer("BMW", "Germany");
        // test other methods from ManufacturerDao

        manufacturerDao.create(manufacturer);
        System.out.println("All data from manufacturers table :");
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println("Data from manufacturers table with id 8:");
        System.out.println(manufacturerDao.get(8L).get());

        System.out.println("Data in manufacturers table after update:");
        Manufacturer manufacturer2 = manufacturerDao.get(8L).get();
        manufacturer2.setName("Ferrari");
        manufacturer2.setCountry("Italy");
        manufacturerDao.update(manufacturer2);
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println("Data in manufacturers table after delete:");
        manufacturerDao.delete(8L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
