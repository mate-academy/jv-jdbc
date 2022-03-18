package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDaoImpl manufacturerDao =
                (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Audi");
        manufacturer.setCountry("Germany");

        manufacturerDao.add(manufacturer);

        long id = 2L;
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(id);
        System.out.println(optionalManufacturer.orElseThrow(() ->
                new NoSuchElementException("Can't find manufacturer by id: " + id)));

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer1: manufacturers) {
            System.out.println(manufacturer1);
        }

        Manufacturer updateManufacturer = new Manufacturer(4L, "Ferrari", "Italy");
        manufacturerDao.update(updateManufacturer);

        System.out.println(manufacturerDao.delete(2L));
    }
}

