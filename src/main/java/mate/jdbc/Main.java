package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final long MANUFACTURER1_ID = 1L;
    private static final long MANUFACTURER2_ID = 2L;
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Daewoo");
        manufacturer1.setCountry("South Korea");
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("Nissan");
        manufacturer2.setCountry("Japan");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(manufacturer1);
        manufacturerDao.create(manufacturer2);
        System.out.println("Get first manufacturer:");
        Optional<Manufacturer> secondManufacturer = manufacturerDao.get(MANUFACTURER1_ID);
        secondManufacturer.ifPresent(System.out::println);
        System.out.println("Get all manufacturers:");
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);
        System.out.println("Updated manufacturer:");
        Manufacturer newManufacturer2 = new Manufacturer();
        newManufacturer2.setId(MANUFACTURER2_ID);
        newManufacturer2.setName("Ford");
        newManufacturer2.setCountry("USA");
        Manufacturer updatedManufacturer = manufacturerDao.update(newManufacturer2);
        System.out.println(updatedManufacturer);
        boolean methodDeleteResult = manufacturerDao.delete(MANUFACTURER2_ID);
        if (methodDeleteResult) {
            System.out.println("Second manufacturer was deleted");
        }
    }
}
