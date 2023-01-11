package mate.jdbc;

import java.util.Arrays;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer[] manufacturers = new Manufacturer[]{
                new Manufacturer("BMW", "Germany"),
                new Manufacturer("BMW", "Germany"),
                new Manufacturer("Volkswagen", "Germany"),
                new Manufacturer("ZAZ", "Ukraine")
        };
        // add
        Arrays.stream(manufacturers)
                .forEach(m -> {
                    m = manufacturerDao.create(m);
                    System.out.println(m);
                });
        // get all
        List<Manufacturer> manufacturersFromDb = manufacturerDao.getAll();
        manufacturersFromDb.forEach(System.out::println);
        manufacturers[0].setName("Ford");
        manufacturers[0].setCountry("USA");
        manufacturers[1].setName("Ferrari");
        manufacturers[1].setCountry("Italy");
        // update
        manufacturerDao.update(manufacturers[0]);
        manufacturerDao.update(manufacturers[1]);
        // get by index
        System.out.println(manufacturerDao.get(manufacturers[0].getId()).orElseThrow().getName()
                + " vs " + manufacturerDao.get(manufacturers[1].getId()).orElseThrow().getName());
        // delete
        Arrays.stream(manufacturers)
                .forEach(m -> System.out.println(manufacturerDao.delete(m.getId())));
    }
}
