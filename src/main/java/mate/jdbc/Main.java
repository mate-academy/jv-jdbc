package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.DbDao;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(DbDao.class);

        System.out.println("Initial records");
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);

        System.out.println(System.lineSeparator() + "After adding record");
        Manufacturer manufacturer = new Manufacturer("Embraer", "Brazil");
        manufacturerDao.create(manufacturer);
        manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);

        System.out.println(System.lineSeparator() + "Search result of record 8");
        Optional<Manufacturer> manufacturer2 = manufacturerDao.get(8L);
        System.out.println(manufacturer2);

        System.out.println(System.lineSeparator() + "After updating record 2");
        manufacturer = new Manufacturer(2L, "Bombardier", "Seychelles");
        manufacturerDao.update(manufacturer);
        manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);

        System.out.println(System.lineSeparator() + "After deleting record 9");
        boolean deleteResult = manufacturerDao.delete(9L);
        manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);
        System.out.println(deleteResult);
    }
}
