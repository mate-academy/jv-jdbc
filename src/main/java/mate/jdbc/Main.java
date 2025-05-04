package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufactureDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {

    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufactureDao manufacturerDao = (ManufactureDao) injector
                .getInstance(ManufactureDao.class);
        List<Manufacturer> manufacturerList = List.of(
                new Manufacturer("Dodge", "USA"),
                new Manufacturer("Ford", "USA"),
                new Manufacturer("Subaru", "Japan"),
                new Manufacturer("Audi", "German")
        );
        Manufacturer manufacturerToAdd = new Manufacturer("Fiat", "France");
        manufacturerList.forEach(manufacturerDao::create);
        manufacturerDao.create(manufacturerToAdd);
        manufacturerDao.update(manufacturerToAdd);
        manufacturerDao.get(manufacturerList.get(1).getId());
        manufacturerDao.delete(manufacturerList.get(0).getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
