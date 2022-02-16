package mate.jdbc;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Joey");
        manufacturer.setCountry("USA");

        manufacturerDao.create(manufacturer);

        long id = 4L;
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(id);
        System.out.println(optionalManufacturer.orElseThrow(() ->
                new NoSuchElementException("Can't find manufacturer by id: " + id)));

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturerInList : manufacturers) {
            System.out.println(manufacturerInList);
        }

        Manufacturer updateManufacturer = new Manufacturer(4L, "Alice", "France");
        manufacturerDao.update(updateManufacturer);

        System.out.println(manufacturerDao.delete(2L));
    }
}
