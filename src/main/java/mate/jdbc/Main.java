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
                new Manufacturer("TATA", "India"),
                new Manufacturer("Chery", "China"),
                new Manufacturer("KRAZ", "Ukraine")
        );
        manufacturers.forEach(manufacturer -> {
            manufacturer = manufacturerDao.create(manufacturer);
            System.out.println(manufacturer);
        });
        manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(manufacturer -> {
            manufacturer.setName("Diesel " + manufacturer.getName());
            manufacturerDao.update(manufacturer);
            System.out.println(manufacturerDao.get(manufacturer.getId()));
        });
        manufacturers.forEach(manufacturer ->
                System.out.println(manufacturerDao.delete(manufacturer.getId())));
    }
}
