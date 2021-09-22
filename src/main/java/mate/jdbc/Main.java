package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Dao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final String PACKAGE_NAME = "mate.jdbc";

    public static void main(String[] args) {
        Injector injector = Injector.getInstance(PACKAGE_NAME);
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(Dao.class);

        System.out.println("Initial records");
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);

        System.out.println(System.lineSeparator() + "After adding record");
        Manufacturer manufacturer = new Manufacturer("MERCEDES", "GERMANY");
        manufacturerDao.create(manufacturer);
        manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);

        System.out.println(System.lineSeparator() + "Search result of record 8");
        Optional<Manufacturer> manufacturer8 = manufacturerDao.get(8L);
        System.out.println(manufacturer8);

        System.out.println(System.lineSeparator() + "After updating record 2");
        Manufacturer manufacturer2 = manufacturerDao.get(2L).get();
        manufacturer2.setName("Honda");
        manufacturer2.setCountry("Japan");
        manufacturerDao.update(manufacturer2);
        manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);

        System.out.println(System.lineSeparator() + "After deleting record 9");
        Manufacturer manufacturer9 = manufacturerDao.get(9L).get();
        boolean deleteResult = manufacturerDao.delete(manufacturer9.getId());
        manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);
        System.out.println(deleteResult);
    }
}
