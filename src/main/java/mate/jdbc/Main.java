package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Long INDEX = 3L;
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        // create
        System.out.println("Create manufacturers in DB:");
        List<Manufacturer> manufacturerList =
                List.of(new Manufacturer("BMW", "German"),
                        new Manufacturer("DEO", "Ukraine"),
                        new Manufacturer("Toyota", "Japan"),
                        new Manufacturer("Mercedes", "German"));
        for (Manufacturer manufacturer : manufacturerList) {
            System.out.println(manufacturerDao.create(manufacturer));
        }
        // getAll
        System.out.println("Get all data from DB:");
        manufacturerDao.getAll().forEach(System.out::println);
        // get
        System.out.println("Get manufacturer by index:");
        Manufacturer manufacturerByIndex = manufacturerDao.get(INDEX).orElseThrow();
        System.out.println(manufacturerByIndex);
        // update
        System.out.println("Update manufacturer in DataBase:");
        manufacturerByIndex.setName("Kia");
        Manufacturer updateManufacturer = manufacturerDao.update(manufacturerByIndex);
        System.out.println(updateManufacturer);
        //delete
        System.out.println("Delete manufacturer in DataBase:");
        boolean deleted = manufacturerDao.delete(INDEX);
        System.out.println(deleted);
    }
}
