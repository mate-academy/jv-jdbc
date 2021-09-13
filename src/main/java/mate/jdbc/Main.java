package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer(1L, "mercedes", "germany");
        manufacturerDao.create(manufacturer);
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(1L);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        Manufacturer otherManufacturer = new Manufacturer(1L, "vw", "germany");
        manufacturerDao.update(otherManufacturer);
        manufacturerDao.delete(1L);
    }
}
