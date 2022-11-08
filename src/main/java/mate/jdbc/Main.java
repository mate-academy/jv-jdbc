package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturersDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.manufacturer.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        Manufacturer tesla = new Manufacturer("Tesla", "USA");
        ManufacturersDao manufacturersDao
                = (ManufacturersDao) injector.getInstance(ManufacturersDao.class);
        manufacturersDao.create(bmw);
        manufacturersDao.create(tesla);
        List<Manufacturer> all = manufacturersDao.getAll();
        Manufacturer bmw1 = all.stream()
                .filter(x -> x.getName().equals("BMW"))
                .findFirst()
                .orElseThrow();
        bmw1.setCountry("China");
        manufacturersDao.update(bmw1);
        Manufacturer tesla1 = all.stream()
                .filter(x -> x.getName().equals("Tesla"))
                .findFirst()
                .orElseThrow();
        Optional<Manufacturer> manufacturer = manufacturersDao.get(tesla1.getId());
        manufacturersDao.delete(tesla1.getId());
        manufacturersDao.getAll().forEach(System.out::println);
    }
}
