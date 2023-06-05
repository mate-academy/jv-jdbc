package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer newManufacturer = new Manufacturer("Nissan", "Japan");
        Manufacturer createdManufacturer = manufacturerDao.create(newManufacturer);
        System.out.println(createdManufacturer);

        boolean delete = manufacturerDao.delete(2L);
        System.out.println(delete);

        Optional<Manufacturer> manufacturer = manufacturerDao.get(1L);
        manufacturer.ifPresent(System.out::println);

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);

        Manufacturer updatedManufacturer = manufacturerDao.update(createdManufacturer);
        System.out.println(updatedManufacturer);
    }
}
