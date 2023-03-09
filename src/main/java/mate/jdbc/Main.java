package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.dao.ManufacturersDao;
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

        ManufacturersDao manufacturersDao =
                (ManufacturerDaoImpl) injector.getInstance(ManufacturersDao.class);
        manufacturersDao.create(toyota);
        manufacturersDao.create(bmw);
        manufacturersDao.create(fiat);
        manufacturersDao.create(ford);
        manufacturersDao.create(zaz);

        List<Manufacturer> manufacturerList = manufacturersDao.getAll();
        Optional<Manufacturer> manufacturer = manufacturersDao.get(toyota.getId());
        manufacturersDao.delete(ford.getId());
        manufacturersDao.update(toyota);
    }
}
