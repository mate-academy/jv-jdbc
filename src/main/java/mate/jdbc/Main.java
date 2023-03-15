package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        final Manufacturer manufacturerCreate
                = manufacturerDao.create(new Manufacturer(null, "uklon", "Ukraine"));
        System.out.println(manufacturerCreate);
        final Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(1L);
        System.out.println(optionalManufacturer);
        final List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        System.out.println(allManufacturers);
        final Manufacturer updateManufacturer
                = manufacturerDao.update(new Manufacturer(1L, "bolt", "Ukraine"));
        System.out.println(updateManufacturer);
        final boolean deleteManufacturer = manufacturerDao.delete(1L);
        System.out.println(deleteManufacturer);
    }
}
