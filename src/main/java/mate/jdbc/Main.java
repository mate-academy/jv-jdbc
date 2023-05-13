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
        manufacturerList().forEach(manufacturerDao::create);
        System.out.println(manufacturerDao.delete(2L));
        System.out.println(manufacturerDao.get(3L));
        manufacturerDao.update(new Manufacturer(1L,"Mitsubishi","Japan"));
        manufacturerDao.getAll().forEach(System.out::println);
    }

    private static List<Manufacturer> manufacturerList() {
        return List.of(
                new Manufacturer("BMW", "Germany"),
                new Manufacturer("Renault", "France"),
                new Manufacturer("Nissan", "Japan"),
                new Manufacturer("Toyota", "Japan"),
                new Manufacturer("Ford", "USA"));
    }
}
