package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao = (ManufacturerDao) injector
            .getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        List<Manufacturer> manufacturerList = List.of(
                new Manufacturer("Toyota", "Japan"),
                new Manufacturer("Buick", "USA"),
                new Manufacturer("Aston Martin", "UK"),
                new Manufacturer("Tesla", "USA"),
                new Manufacturer("Porsche", "Germany"),
                new Manufacturer("Bugatti", "France"),
                new Manufacturer("Pagani Automobili", "Italy"),
                new Manufacturer("Seat", "Spain")
        );
        manufacturerList.forEach(manufacturer -> {
            manufacturerDao.create(manufacturer);
            System.out.println(manufacturer);
        });
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(manufacturerList.get(1).getId()));
        manufacturerList.get(6).setName("Maserati");
        System.out.println(manufacturerDao.update(manufacturerList.get(1)));
        System.out.println(manufacturerDao.get(manufacturerList.get(2).getId()));
        System.out.println(manufacturerDao.delete(manufacturerList.get(1).getId()));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
