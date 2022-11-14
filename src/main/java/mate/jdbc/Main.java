package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturersDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturersDao.create(new Manufacturer("Audi", "Germany"));
        manufacturersDao.create(new Manufacturer("Lamborghini", "Italy"));
        List<Manufacturer> data = manufacturersDao.getAll();
        Manufacturer audi = data.stream()
                .filter(e -> e.getName().equals("Audi"))
                .findFirst()
                .orElseThrow();
        audi.setCountry("USA");
        manufacturersDao.update(audi);
        Manufacturer lamborghini = data.stream()
                .filter(x -> x.getName().equals("Lamborghini"))
                .findFirst()
                .orElseThrow();
        Optional<Manufacturer> manufacturer = manufacturersDao.get(lamborghini.getId());
        System.out.println(manufacturer.isPresent());
        manufacturersDao.delete(lamborghini.getId());
        manufacturersDao.getAll().forEach(System.out::println);
    }
}
