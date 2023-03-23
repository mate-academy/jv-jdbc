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
        Manufacturer bmw = new Manufacturer();
        bmw.setName("BMW");
        bmw.setCountry("Germany");
        System.out.println(manufacturerDao.create(bmw)
                + "created manufacturer");
        System.out.println(manufacturerDao.get(bmw.getId())
                + "manufacturer we got");
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }
        System.out.println("all manufacturers");
        bmw.setName("MINI");
        System.out.println(manufacturerDao.update(bmw)
                + "updated manufacturer");
        System.out.println("is manufacture deleted"
                + manufacturerDao.delete(bmw.getId()));
    }
}
