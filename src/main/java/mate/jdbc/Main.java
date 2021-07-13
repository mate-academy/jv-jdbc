package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        printAll();
        Manufacturer manufacturer = manufacturerDao.create(new Manufacturer("name2", "country"));
        printAll();
        manufacturer.setCountry("country1");
        manufacturerDao.update(manufacturer);
        System.out.println(manufacturerDao.get(manufacturer.getId()));
        printAll();
        manufacturerDao.delete(manufacturer.getId());
        printAll();
    }

    private static void printAll() {
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        System.out.println();
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);
    }
}
