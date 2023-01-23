package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturerList = List.of(
                new Manufacturer("Honda", "Japan"),
                new Manufacturer("Peugeot", "China")
        );
        manufacturerList.forEach(manufacturerDao::create);
        Manufacturer testManufacturer = manufacturerDao.get(2L).orElseThrow();
        testManufacturer.setCountry("France");
        manufacturerDao.update(testManufacturer);
        manufacturerDao.delete(testManufacturer.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
