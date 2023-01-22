package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

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
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerToAdd = new Manufacturer(40L, "Maserati", "Italy");
        manufacturerList.forEach(manufacturerDao::create);
        manufacturerDao.update(manufacturerToAdd);
        manufacturerDao.get(2L);
        manufacturerDao.delete(1L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
