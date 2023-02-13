package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final int MANUFACTURER_INDEX = 5;
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        //add data to DB
        System.out.println("Added data to DB:");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = List.of(new Manufacturer("Lincoln", "USA"),
                new Manufacturer("ZAZ", "Ukraine"),
                new Manufacturer("BMW", "Germany"),
                new Manufacturer("Nissan", "Japan"),
                new Manufacturer("KIA", "Korea"),
                new Manufacturer("WrongName", "USA"));
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturerDao.create(manufacturer));
        }
        //get manufacturer from DB by id
        long manufacturerId = manufacturers.get(MANUFACTURER_INDEX).getId();
        System.out.println("Get manufacturer from DB with id = " + manufacturerId);
        Manufacturer manufacturer = manufacturerDao.get(manufacturerId).orElseThrow();
        System.out.println(manufacturer);
        //rename manufacturer and update it
        System.out.println("Get updated manufacturer with id = " + manufacturerId);
        manufacturer.setName("Jeep");
        System.out.println(manufacturerDao.update(manufacturer));
        // soft delete manufacture from DB
        manufacturerDao.delete(manufacturerId);
        System.out.println("Data from DB without deleted manufacture id = " + manufacturerId);
        manufacturerDao.getAll().forEach(System.out::println);

    }
}
