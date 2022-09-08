package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("ZAZ");
        manufacturer.setCountry("Ukraine");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        System.out.println("Test creating:");
        System.out.println(manufacturerDao.create(manufacturer));

        System.out.println("Test getting by some id:");
        Optional<Manufacturer> gottenManufacturer = manufacturerDao.get(manufacturer.getId());
        System.out.println(gottenManufacturer);

        System.out.println("Test updating:");
        manufacturer.setName("LuAZ");
        System.out.println(manufacturerDao.update(manufacturer));

        System.out.println("Test deleting:");
        boolean deletedManufacturer = manufacturerDao.delete(manufacturer.getId());
        System.out.println(deletedManufacturer);

        System.out.println("Test getting all manufacturers:");
        System.out.println(manufacturerDao.getAll());
    }
}
