package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer(1L, "mercedes", "germany");
        manufacturerDao.create(manufacturer);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
        System.out.println(manufacturerDao.get(1L));
        Manufacturer otherManufacturer = new Manufacturer(1L, "vw", "germany");
        manufacturerDao.update(otherManufacturer);
        manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
        manufacturerDao.delete(1L);
        manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
    }
}
