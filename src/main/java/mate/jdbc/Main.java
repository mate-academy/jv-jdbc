package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final Long VALID_ID = 2L;
    private static final Long INVALID_ID = 10L;

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        manufacturerDao.clearManufacturersTable();

        Manufacturer sonyManufacturer = new Manufacturer();
        sonyManufacturer.setName("Sony");
        sonyManufacturer.setCountry("Japan");

        Manufacturer samsungManufacturer = new Manufacturer();
        samsungManufacturer.setName("Samsung");
        samsungManufacturer.setCountry("South Korea");

        Manufacturer asusManufacturer = new Manufacturer();
        asusManufacturer.setName("Asus");
        asusManufacturer.setCountry("Taiwan");

        System.out.println(manufacturerDao.create(sonyManufacturer));
        System.out.println(manufacturerDao.create(samsungManufacturer));
        System.out.println(manufacturerDao.create(asusManufacturer));

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : allManufacturers) {
            System.out.println(manufacturer);
        }

        System.out.println(manufacturerDao.get(VALID_ID).get());
        if (manufacturerDao.get(INVALID_ID).isEmpty()) {
            System.out.println("There is no manufacturer with id: " + INVALID_ID);
        }

        sonyManufacturer.setCountry("JPN");
        System.out.println(manufacturerDao.update(sonyManufacturer));

        System.out.println(manufacturerDao.delete(VALID_ID));
    }
}
