package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturersDao;
import mate.jdbc.dao.ManufacturersDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        Manufacturer mazda = new Manufacturer("Mazda", "Germany");
        Manufacturer opel = new Manufacturer("Opel", "Germany");
        ManufacturersDaoImpl manufacturersDao = (ManufacturersDaoImpl)
                injector.getInstance(ManufacturersDao.class);
        manufacturersDao.create(mazda);
        manufacturersDao.create(opel);
        List<Manufacturer> manufacturers = manufacturersDao.getAll();
        Optional<Manufacturer> manufacturer = manufacturersDao.get(mazda.getId());
        manufacturersDao.delete(opel.getId());
    }
}
