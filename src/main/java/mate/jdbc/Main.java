package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(new Manufacturer("audi", "Italy"));
        manufacturerDao.create(new Manufacturer("volvo", "Germany"));
        manufacturerDao.delete(2L);
        manufacturerDao.update(new Manufacturer(7L, "shkoda", "Japan"));
        Optional<Manufacturer> manufacturerWithIndex9 = manufacturerDao.get(9L);
        Optional<Manufacturer> manufacturerToCompare = Optional.ofNullable(
                new Manufacturer(9L, "volvo", "Germany"));
        boolean compare = manufacturerWithIndex9.equals(manufacturerToCompare);
        System.out.println(compare);
        List<Manufacturer> all = manufacturerDao.getAll();
        System.out.println(all);
    }
}
