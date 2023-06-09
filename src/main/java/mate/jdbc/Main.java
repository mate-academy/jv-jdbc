package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturersList = List.of(
                new Manufacturer("Audi", "Germany"),
                new Manufacturer("Volkswagen", "Germany"),
                new Manufacturer("Fiat", "Italy"),
                new Manufacturer("Toyota", "Japan"),
                new Manufacturer("Volvo", "Sweden"),
                new Manufacturer("Volvo", "USA"));
        manufacturersList.forEach(manufacturerDao::create);

        List<Manufacturer> getAllManufacturersFromDb = manufacturerDao.getAll();
        System.out.println(getAllManufacturersFromDb);

        Manufacturer newManufacturer = new Manufacturer(5L, "Land Rover", "United Kingdom");

        Optional<Manufacturer> getManufacturerById = manufacturerDao.get(5L);
        System.out.println(getManufacturerById);

        manufacturerDao.update(newManufacturer);
        System.out.println(getManufacturerById);

        boolean isDeleted = manufacturerDao.delete(8L);
        System.out.println(isDeleted);
        System.out.println(manufacturerDao.getAll());
    }
}

