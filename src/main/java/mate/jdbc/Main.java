package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDaoImpl manufacturerDao =
                (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(new Manufacturer("Hyundai", "South Korea"));
        manufacturers.add(new Manufacturer("Renault", "France"));
        manufacturers.add(new Manufacturer("Volkswagen", "Germany"));
        manufacturers.forEach(manufacturerDao::create);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(1L).get());
        Manufacturer manufacturer = manufacturerDao.get(1L).get();
        Manufacturer manufacturerUpdate = new Manufacturer(manufacturer.getId(), "Tesla", "USA");
        manufacturerDao.update(manufacturerUpdate);
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(manufacturerUpdate.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
