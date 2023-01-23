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
        Manufacturer manufacturerToAdd = new Manufacturer("Maserati", "Italy");
        manufacturerList.forEach(manufacturerDao::create);
        manufacturerDao.create(manufacturerToAdd);
        manufacturerDao.update(manufacturerToAdd);
        manufacturerDao.get(manufacturerList.get(1).getId());
        manufacturerDao.delete(manufacturerList.get(0).getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
