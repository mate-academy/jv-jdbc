package mate.jdbc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        info("Dao.create: Creating manufacturers:");
        List<Manufacturer> unsavedManufacturers = List.of(
                new Manufacturer(null, "Volvo", "Ukraine"),
                new Manufacturer(null, "Land Rover", "Ukraine"),
                new Manufacturer(null, "Mazda", "Ukraine"));
        List<Manufacturer> savedManufacturers = unsavedManufacturers.stream()
                .map(manufacturerDao::create)
                .peek(System.out::println)
                .collect(Collectors.toList());

        info("Dao.update: Updating country for Mazda manufacturer:");
        savedManufacturers.stream()
                .filter(e -> "Mazda".equals(e.getName()))
                .forEach(e -> {
                    e.setCountry("Japan");
                    manufacturerDao.update(e);
                });
        manufacturerDao.getAll().forEach(System.out::println);

        info("Dao.delete: Deleting Land Rover manufacturer:");
        manufacturerDao.getAll().stream()
                .filter(e -> "Land Rover".equals(e.getName()))
                .map(Manufacturer::getId)
                .forEach(manufacturerDao::delete);
        manufacturerDao.getAll().forEach(System.out::println);

        info("Dao.get: Getting manufacturer by id:");
        Optional<Manufacturer> manufacturerDaoGetResult =
                manufacturerDao.get(savedManufacturers.get(2).getId());
        manufacturerDaoGetResult.ifPresent(System.out::println);
    }

    private static void info(String message) {
        System.out.println(System.lineSeparator() + message);
    }
}
