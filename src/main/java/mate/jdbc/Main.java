package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        System.out.println("Initial data in manufacturers table:");
        manufacturers.stream().forEach(System.out::println);

        Manufacturer manufacturer = new Manufacturer("BMW", "Germany");
        manufacturerDao.create(manufacturer);
        Optional<Manufacturer> manufacturerById3 = manufacturerDao.get(3L);
        System.out.println("Create new manufacturer and find it by id 3:");
        manufacturerById3.ifPresent(System.out::println);

        Manufacturer manufacturerToUpdate = new Manufacturer("Mitsubishi", "Japan");
        Manufacturer updatedManufacturer = manufacturerDao.update(manufacturerToUpdate);
        System.out.println("Updated manufacturer :" + System.lineSeparator()
                + updatedManufacturer);

        if (manufacturerDao.delete(updatedManufacturer.getId())) {
            System.out.println("Manufacturer " + updatedManufacturer + " was deleted");
        }
    }
}
