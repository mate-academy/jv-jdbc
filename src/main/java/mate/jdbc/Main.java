package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = List.of(
                new Manufacturer("Tesla", "USA"),
                new Manufacturer("Jeep", "USA"),
                new Manufacturer("Skoda", "Czech Republic"),
                new Manufacturer("Volvo Cars", "Sweden"),
                new Manufacturer("Mini", "United Kingdom"));
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturerDao.create(manufacturer) + " was created");
        }
        Manufacturer extraManufacturer = manufacturerDao.get(
                manufacturers.get(0).getId()).orElseThrow();
        System.out.println(extraManufacturer + " was successfully deleted: "
                + manufacturerDao.delete(extraManufacturer.getId()));
        Manufacturer wrongManufacturer = manufacturerDao.get(
                manufacturers.get(1).getId()).orElseThrow();
        wrongManufacturer.setName("Dodge");
        System.out.println(manufacturerDao.update(wrongManufacturer) + " was updated");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
