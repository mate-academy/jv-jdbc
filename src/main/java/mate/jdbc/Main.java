package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("BMV", "Germany");
        Manufacturer insertedManufacturer = manufacturerDao.create(manufacturer);
        List<Manufacturer> allFromDB = manufacturerDao.getAll();
        printAllActions(insertedManufacturer, allFromDB);
    }

    private static void printAllActions(Manufacturer insertedManufacturer,
                                 List<Manufacturer> allFromDB) {
        System.out.println("insertedManufacturer = " + insertedManufacturer.toString());
        System.out.println("All from DB:");
        for (Manufacturer manufacturer : allFromDB) {
            System.out.println("manufacturer = " + manufacturer.toString());
        }
    }
}
