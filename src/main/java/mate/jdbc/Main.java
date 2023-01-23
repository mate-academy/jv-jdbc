package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final int SECOND_MANUFACTURER = 777;
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        List<Manufacturer> manufacturers = List.of(
                new Manufacturer("Dodge", "USA"),
                new Manufacturer("Renault", "France"),
                new Manufacturer("Peugeot", "France"),
                new Manufacturer("BMW", "Germany"),
                new Manufacturer("Mercedes", "Germany"),
                new Manufacturer("Hyundai", "Korea"),
                new Manufacturer("Subaru", "Japan")
        );
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerToUpdate =
                manufacturerDao.get(manufacturers.get(SECOND_MANUFACTURER).getId()).orElseThrow();
        manufacturerToUpdate.setName("Jaguar");
        manufacturerToUpdate.setCountry("England");
        manufacturers.forEach(manufacturerDao::create);
        manufacturerDao.get(790L);
        manufacturerDao.update(manufacturerToUpdate);
        manufacturerDao.delete(787L);
        manufacturerDao.delete(789L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
