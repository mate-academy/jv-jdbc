package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer audi = new Manufacturer("Audi", "Germany");
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        manufacturerDao.create(audi);
        manufacturerDao.create(bmw);
        List<Manufacturer> all = manufacturerDao.getAll();
        Optional<Manufacturer> manufacturer = manufacturerDao.get(audi.getId());
        Optional<Manufacturer> manufacturer1 = manufacturerDao.get(bmw.getId());
        Manufacturer update = manufacturerDao.update(manufacturer1.get());
        boolean delete = manufacturerDao.delete(bmw.getId());
    }
}
