package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        Manufacturer toyota = new Manufacturer("TOYOTA", "Japan");
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        Manufacturer fiat = new Manufacturer("FIAT", "Italy");
        Manufacturer ford = new Manufacturer("Ford", "USA");
        Manufacturer zaz = new Manufacturer("ZAZ", "Ukraine");

        ManufacturerDao manufacturerDao =
                (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(toyota);
        manufacturerDao.create(bmw);
        manufacturerDao.create(fiat);
        manufacturerDao.create(ford);
        manufacturerDao.create(zaz);

        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        Optional<Manufacturer> manufacturer = manufacturerDao.get(toyota.getId());
        manufacturerDao.delete(ford.getId());
        manufacturerDao.update(toyota);
    }
}
