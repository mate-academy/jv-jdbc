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
        Manufacturer bmw = new Manufacturer("BMV", "Ukraine");
        Manufacturer lada = new Manufacturer("Lada", "Germany");

        bmw = manufacturerDao.create(bmw);
        lada = manufacturerDao.create(lada);
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(2L);
        List<Manufacturer> manufacturerAll = manufacturerDao.getAll();
        lada.setCountry("Soviet Union");
        manufacturerDao.update(lada);
        manufacturerDao.delete(lada.getId());
    }
}
