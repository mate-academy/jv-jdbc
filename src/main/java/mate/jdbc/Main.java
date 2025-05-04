package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final int ID_MANUFACTURER_TO_UPDATE = 5;
    private static final int ID_MANUFACTURER_TO_GET = 6;
    private static final int ID_MANUFACTURER_TO_DELETE = 3;
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

        manufacturers.forEach(manufacturerDao::create);

        Manufacturer manufacturerToGet =
                manufacturerDao.get(manufacturers.get(ID_MANUFACTURER_TO_GET)
                        .getId()).orElseThrow();
        manufacturerDao.get(manufacturerToGet.getId());

        Manufacturer manufacturerToUpdate =
                manufacturerDao.get(manufacturers.get(ID_MANUFACTURER_TO_UPDATE)
                        .getId()).orElseThrow();
        manufacturerToUpdate.setName("Jaguar");
        manufacturerToUpdate.setCountry("England");
        manufacturerDao.update(manufacturerToUpdate);

        Manufacturer manufacturerToDelete =
                manufacturerDao.get(manufacturers.get(ID_MANUFACTURER_TO_DELETE)
                        .getId()).orElseThrow();
        manufacturerDao.delete(manufacturerToDelete.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
