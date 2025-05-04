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
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");
        Manufacturer createdToyota = manufacturerDao.create(toyota);
        System.out.println(createdToyota);

        Optional<Manufacturer> manufacturer = manufacturerDao.get(toyota.getId());
        manufacturer.ifPresent(System.out::println);

        createdToyota.setCountry("Turkey");
        Manufacturer updatedToyota = manufacturerDao.update(createdToyota);
        System.out.println(updatedToyota);

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);

        boolean delete = manufacturerDao.delete(toyota.getId());
        System.out.println(delete);
    }
}
