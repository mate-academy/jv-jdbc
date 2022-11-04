package mate.jdbc;

import java.util.List;
import java.util.Optional;
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
        Optional<Manufacturer> manufacturerFromDB = manufacturerDao.get(2L);
        List<Manufacturer> allFromDB = manufacturerDao.getAll();
        Manufacturer updateManufacturer = new Manufacturer(7L, "Volkswagen", "Germany");
        Manufacturer updatedManufacturer = manufacturerDao.update(updateManufacturer);
        boolean deleted = manufacturerDao.delete(6L);
        printAllActions(insertedManufacturer, manufacturerFromDB,
                allFromDB, updatedManufacturer, deleted);
    }

    private static void printAllActions(Manufacturer insertedManufacturer,
                                        Optional<Manufacturer> manufacturerFromDB,
                                 List<Manufacturer> allFromDB, Manufacturer updatedManufacturer,
                                 boolean deleted) {
        System.out.println("insertedManufacturer = " + insertedManufacturer.toString());
        System.out.println("manufacturerFromDB = " + manufacturerFromDB.toString());
        System.out.println("All from DB:");
        for (Manufacturer manufacturer : allFromDB) {
            System.out.println("manufacturer = " + manufacturer.toString());
        }
        System.out.println("updatedManufacturer = " + updatedManufacturer.toString());
        System.out.println("deleted = " + deleted);
    }
}
