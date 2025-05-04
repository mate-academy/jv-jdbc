package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        System.out.println("Manufacturers creating");
        Manufacturer daewoo = manufacturerDao.create(new Manufacturer("DAEWOO", "South Korea"));
        System.out.println(daewoo);
        Manufacturer scoda = manufacturerDao.create(new Manufacturer("Scoda", "Czech Republic"));
        System.out.println(scoda);
        Manufacturer lexus = manufacturerDao.create(new Manufacturer("Lexus", "Japan"));
        System.out.println(lexus);
        Manufacturer toyota = manufacturerDao.create(new Manufacturer("Toyota", "Japan"));
        System.out.println(toyota);
        System.out.println("\n Manufacturer updating Daewoo to DWO");
        daewoo.setName("DWO");
        Manufacturer newDaewoo = manufacturerDao.update(daewoo);
        System.out.println(newDaewoo);
        System.out.println("\n Manufacturer getting by Id DWO");
        Manufacturer daewooFromDb = manufacturerDao.get(daewoo.getId()).get();
        System.out.println(daewooFromDb);
        System.out.println("\n Manufacturer deleting by Id Lexus");
        System.out.println(manufacturerDao.delete(lexus.getId()));
        System.out.println("\n Manufacturer getting all existing manufacturers");
        List<Manufacturer> manufacturersFromDb = manufacturerDao.getAll();
        manufacturersFromDb.forEach(System.out::println);
    }
}
