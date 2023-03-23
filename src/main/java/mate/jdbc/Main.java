package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer bmw = new Manufacturer(1L, "BMW", "Germany");
        manufacturerDao.create(bmw);
        manufacturerDao.get(bmw.getId());
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }
        bmw.setName("MINI");
        manufacturerDao.update(bmw);
        manufacturerDao.delete(bmw.getId());
    }
}
