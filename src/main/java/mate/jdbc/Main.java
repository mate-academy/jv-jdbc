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
        Manufacturer nissan = new Manufacturer("Nissan", "Japan");
        Manufacturer createdManufacturer = manufacturerDao.create(nissan);
        System.out.println(createdManufacturer);

        Optional<Manufacturer> manufacturer = manufacturerDao.get(nissan.getId());
        manufacturer.ifPresent(System.out::println);

        createdManufacturer.setCountry("Ukraine");
        Manufacturer updatedManufacturer = manufacturerDao.update(createdManufacturer);
        System.out.println(updatedManufacturer);

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);

        boolean delete = manufacturerDao.delete(nissan.getId());
        System.out.println(delete);
    }
}
