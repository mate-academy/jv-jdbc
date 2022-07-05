package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(
                ManufacturerDao.class);
        System.out.println("* The original data in table:");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("* The data after adding a new manufacturer (Ford, USA):");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Ford");
        manufacturer.setCountry("USA");
        Manufacturer createdManufacturer = manufacturerDao.create(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("* Get the newly created manufacturer");
        Long id = createdManufacturer.getId();
        Optional<Manufacturer> obtainedManufacturer = manufacturerDao.get(id);
        System.out.println(obtainedManufacturer);
        System.out.println("* The data in table after updating the newly created manufacturer");
        Manufacturer manufacturerToUpdate = new Manufacturer();
        manufacturerToUpdate.setId(id);
        manufacturerToUpdate.setName("Toyota");
        manufacturerToUpdate.setCountry("Japan");
        manufacturerDao.update(manufacturerToUpdate);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("* The data in table after deleting the manufacturer "
                + "with id of newly created manufacturer");
        manufacturerDao.delete(id);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
