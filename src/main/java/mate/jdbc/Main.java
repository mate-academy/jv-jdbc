package mate.jdbc;

import java.util.List;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.models.ManufacturerDao;

public class Main {
    private static final Injector injector = Injector.getInstance("YOUR_PACKAGE");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Jonny");
        manufacturer.setId(Long.valueOf(1));
        manufacturer.setCountry("OpaLand");
        Dao manufacturerDao = (ManufacturerDao) injector.getInstance(Dao.class);
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
