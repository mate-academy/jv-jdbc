package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer teslaManufacturer = new Manufacturer("Tesla", "USA");
        Manufacturer hondaManufacturer = new Manufacturer("Honda Motor", "Japan");

        manufacturerDao.create(teslaManufacturer);
        manufacturerDao.create(hondaManufacturer);

        System.out.println("Get Tesla manufacturer:");
        Optional<Manufacturer> optionalManufacturer =
                manufacturerDao.get(teslaManufacturer.getId());
        optionalManufacturer.ifPresent(System.out::println);

        System.out.println("Update Honda Motor manufacturer:");
        hondaManufacturer.setName("Honda");
        manufacturerDao.update(hondaManufacturer);

        System.out.println("Get all manufacturers:");
        System.out.println(manufacturerDao.getAll());

        System.out.println("Get all manufacturers after delete Tesla:");
        manufacturerDao.delete(teslaManufacturer.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
