package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerService =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = List.of(
                new Manufacturer("BMW", "Germany"),
                new Manufacturer("BMW", "Germany"),
                new Manufacturer("ZAZ", "Ukraine")
        );
        manufacturers.forEach(manufacturer -> {
            manufacturer = manufacturerService.create(manufacturer);
            System.out.println(manufacturer);
        });
        manufacturers = manufacturerService.getAll();
        manufacturers.forEach(System.out::println);
        manufacturers.forEach(manufacturer -> {
            manufacturer.setName("new " + manufacturer.getName());
            manufacturerService.update(manufacturer);
            System.out.println(manufacturerService.get(manufacturer.getId()).orElseThrow());
        });
        manufacturers.forEach(manufacturer ->
                System.out.println(manufacturerService.delete(manufacturer.getId())));
    }
}
