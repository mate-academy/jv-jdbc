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
        Manufacturer lexus = new Manufacturer();
        lexus.setName("Lexus");
        lexus.setCountry("USA");
        manufacturerDao.create(lexus);
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);
        lexus.setCountry("Japan");
        manufacturerDao.update(lexus);
        Manufacturer manufacturer = manufacturerDao.get(1L).orElse(new Manufacturer());
        System.out.println(manufacturer);
        manufacturerDao.delete(1L);
    }
}
