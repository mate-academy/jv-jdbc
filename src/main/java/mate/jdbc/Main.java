package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer(Long.valueOf(1), "Jonny", "OpaLand");
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(manufacturer);
        List<Manufacturer> list = manufacturerDao.getAll();
        for (Manufacturer manufacturer1 : list) {
            System.out.println(manufacturer1.toString());
        }
        manufacturerDao.delete(2L);
        System.out.println(manufacturerDao.get(2L).toString());
        manufacturer.setName("Milosh");
        manufacturer.setCountry("Slovakia");
        manufacturer.setId(2L);
        manufacturerDao.update(manufacturer);
        System.out.println(manufacturerDao.get(2L));
    }
}
