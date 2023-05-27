package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        List<Manufacturer> manufacturers = List.of(new Manufacturer().setName("Mazda").setCountry("Japan"),
                new Manufacturer().setName("Ford").setCountry("USA"),
                new Manufacturer().setName("Mercedes").setCountry("Germany"));
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturers.forEach(manufacturerDao::create);
        Manufacturer manufacturerFromDb = manufacturerDao.get(manufacturers.get(0).getId())
                .orElseGet(Manufacturer::new);
        manufacturerFromDb.setName("Lexus").setCountry("Japan");
        manufacturerDao.update(manufacturerFromDb);
        manufacturerDao.delete(manufacturerFromDb.getId());
        List<Manufacturer> manufacturersFromDb = manufacturerDao.getAll();
        manufacturersFromDb.forEach(System.out::println);
    }
}
