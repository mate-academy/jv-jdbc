package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Long TEST_ID = 2L;
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerSuzuki = new Manufacturer("Suzuki", "Japan");
        Manufacturer manufacturerFord = new Manufacturer("Ford", "USA");
        System.out.println("Create test:");
        manufacturerDao.create(manufacturerSuzuki);
        manufacturerDao.create(manufacturerFord);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Read test:");
        Optional<Manufacturer> manufacturer = manufacturerDao.get(TEST_ID);
        System.out.println(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Update test:");
        Manufacturer manufacturerUpdate = new Manufacturer(TEST_ID, "Citroen", "France");
        manufacturerDao.update(manufacturerUpdate);
        Optional<Manufacturer> checkUpdateManufacturer = manufacturerDao.get(TEST_ID);
        System.out.println(checkUpdateManufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Delete test:");
        manufacturerDao.delete(TEST_ID);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
