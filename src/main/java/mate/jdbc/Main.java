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

        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        Manufacturer audi = new Manufacturer("Audi", "Canada");
        Manufacturer ford = new Manufacturer("Ford","USA");
        Manufacturer renault = new Manufacturer("Ferrari", "Italy");
        manufacturerDao.create(bmw);
        manufacturerDao.create(audi);
        manufacturerDao.create(ford);
        manufacturerDao.create(renault);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        Optional<Manufacturer> manufacturer = manufacturerDao.get(3L);
        boolean delete = manufacturerDao.delete(3L);
        audi.setName("SuperAudi");
        Manufacturer update = manufacturerDao.update(audi);
    }
}
