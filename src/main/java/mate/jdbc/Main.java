package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Fiat");
        manufacturer.setCountry("Italy");
        System.out.println(manufacturer = manufacturerDao.create(manufacturer));
        manufacturer.setName("Lancia");
        System.out.println(manufacturerDao.update(manufacturer));
        System.out.println(manufacturerDao.get(2L).toString());
        manufacturerDao.delete(3L);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(m -> System.out.println(m.toString()));
    }
}
