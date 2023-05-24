package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        System.out.println("App.start");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("HP");
        manufacturer.setCountry("USA");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("BMV");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        manufacturerDao.getAll();
        System.out.println("\n");
        System.out.println(manufacturerDao.get(manufacturer.getId()).toString());
        manufacturer.setName("VW");
        manufacturerDao.update(manufacturer);
        System.out.println(manufacturerDao.get(manufacturer.getId()).toString());
        List<Manufacturer> list = manufacturerDao.getAll();
        list.forEach(System.out::println);
        System.out.println("\n");
        manufacturerDao.delete(manufacturer.getId());
        list = manufacturerDao.getAll();
        list.forEach(System.out::println);
        System.out.println("App.finish");
    }
}
