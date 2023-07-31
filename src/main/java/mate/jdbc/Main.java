package mate.jdbc;

import lombok.extern.log4j.Log4j2;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.Injector;

@Log4j2
public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        // Create new Manufacturers
        Manufacturer manufacturerMercedes = new Manufacturer();
        manufacturerMercedes.setName("Dodge Challenger");
        manufacturerMercedes.setCountry("USA");
        manufacturerDao.create(manufacturerMercedes);

        Manufacturer manufacturerToyota = new Manufacturer();
        manufacturerToyota.setName("Toyota Prius");
        manufacturerToyota.setCountry("Japan");
        manufacturerDao.create(manufacturerToyota);

        // Get all Manufacturers
        System.out.println("All Manufacturers:");
        manufacturerDao.getAll().forEach(System.out::println);

        // Update Manufacturer (Mercedes)
        Manufacturer manufacturerMercedesUpdate = new Manufacturer();
        manufacturerMercedesUpdate.setId(1L);
        manufacturerMercedesUpdate.setName("Mercedes-Benz");
        manufacturerMercedesUpdate.setCountry("Germany");
        manufacturerDao.update(manufacturerMercedesUpdate);

        // Get all Manufacturers after update
        System.out.println("All Manufacturers after update:");
        manufacturerDao.getAll().forEach(System.out::println);

        // Delete Manufacturer by id (Toyota)
        manufacturerDao.delete(6L);

        // Get all Manufacturers after delete
        System.out.println("All Manufacturers after delete:");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
