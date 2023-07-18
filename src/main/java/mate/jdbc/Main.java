package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.impl.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) INJECTOR.getInstance(ManufacturerDao.class);
//        Manufacturer manufacturer1 = new Manufacturer();
//        manufacturer1.setName("BMW");
//        manufacturer1.setCountry("Germany");
//        Manufacturer result = manufacturerDao.create(manufacturer1);
//        System.out.println(result);
//        List<Manufacturer> all = manufacturerDao.getAll();
//        manufacturerDao.get(1L);

    }
}
