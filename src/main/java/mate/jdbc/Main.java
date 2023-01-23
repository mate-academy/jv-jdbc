package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer lanosManufacturer = new Manufacturer("Lanos", "Ukraine");
        Manufacturer volvoManufacturer = new Manufacturer("Volvo", "China");
        manufacturerDao.create(lanosManufacturer);
        manufacturerDao.create(volvoManufacturer);
        Optional<Manufacturer> manufacturer =
                manufacturerDao.get(volvoManufacturer.getId());
        System.out.println(manufacturer);
        lanosManufacturer.setName("Lanos");
        manufacturerDao.update(lanosManufacturer);
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(volvoManufacturer.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
