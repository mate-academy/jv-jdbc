package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer(1L,"Toyota","Japan");
        manufacturerDao.create(manufacturer);
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(manufacturer.getId());
        System.out.println(optionalManufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer newManufacturer = new Manufacturer(2L, "Volvo", "Sweden");
        manufacturerDao.update(newManufacturer);
        System.out.println(manufacturerDao.delete(newManufacturer.getId()));
        System.out.println(manufacturerDao.update(manufacturer));
    }
}
