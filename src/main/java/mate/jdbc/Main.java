package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Mazda", "Japan");
        manufacturer = manufacturerDao.create(manufacturer);
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(manufacturer.getId());
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
        System.out.println();
        manufacturer.setId(1L);
        System.out.println(manufacturerDao.update(manufacturer));
        System.out.println(manufacturerDao.delete(6L));
    }
}
