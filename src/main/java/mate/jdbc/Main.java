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

        Manufacturer updatedManufacturerById3 = manufacturerDao
                .update(new Manufacturer(3L, "Mitsubishi", "Japan"));
        System.out.println("Updated manufacturer by id 3:" + System.lineSeparator()
                + updatedManufacturerById3);

        if (manufacturerDao.delete(6L)) {
            System.out.println("Manufacturer by id 6 was deleted");
        }
    }
}
