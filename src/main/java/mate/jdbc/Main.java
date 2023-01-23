package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final int FIRST_MANUFACTURER = 0;
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = List.of(new Manufacturer("Ford", "USA"),
                new Manufacturer("Fiat", "Italy"),
                new Manufacturer("BMW", "Germany"),
                new Manufacturer("Nissan", "Japan"));
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer = manufacturerDao.create(manufacturer));
        }
        Manufacturer manufacturer = manufacturerDao.get(
                manufacturers.get(FIRST_MANUFACTURER).getId()).orElseThrow();
        System.out.println(manufacturer);
        manufacturer.setName("General Motors");
        System.out.println(manufacturerDao.update(manufacturer));
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
