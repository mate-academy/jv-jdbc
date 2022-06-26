package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Audi");
        manufacturer.setCountry("Germany");

        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);

        System.out.println("Create a new manufacturer");
        Manufacturer manufacturerResult = manufacturerDao.create(manufacturer);
        System.out.println(manufacturerResult);

        System.out.println("\nAll manufacturers");
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);

        System.out.println("\nManufacturers by ID");
        System.out.println(manufacturerDao.get(manufacturer.getId()));

        System.out.println("\nUpdate a manufacturer");
        manufacturer.setName("Porsche");
        System.out.println(manufacturerDao.update(manufacturer));

        System.out.println("\nDelete a manufacturer by id " + manufacturer.getId());
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
    }
}
