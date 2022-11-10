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
        Manufacturer ford = new Manufacturer("Ford", "USA");
        Manufacturer renault = new Manufacturer("Renault", "Japan");
        Manufacturer audi = new Manufacturer("Audi", "Canada");
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        manufacturerDao.create(ford);
        manufacturerDao.create(renault);
        manufacturerDao.create(audi);
        manufacturerDao.create(bmw);
        List<Manufacturer> all = manufacturerDao.getAll();
        Optional<Manufacturer> manufacturer = manufacturerDao.get(2L);
        boolean delete = manufacturerDao.delete(2L);
        Optional<Manufacturer> manufacturer1 = manufacturerDao.get(3L);
        Manufacturer update = manufacturerDao.update(manufacturer1.get());
    }
}
