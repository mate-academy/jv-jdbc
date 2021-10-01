package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer volkswagen = new Manufacturer("Volkswagen", "Germany");
        Manufacturer kia = new Manufacturer("KIA", "South Korea");
        volkswagen = manufacturerDao.create(volkswagen);
        kia = manufacturerDao.create(kia);
        System.out.println(manufacturerDao.getAll());
        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get(1L);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
        volkswagen.setName("VW");
        kia.setCountry("Korea");
        manufacturerDao.update(volkswagen);
        manufacturerDao.update(kia);
        manufacturerDao.delete(volkswagen.getId());
        manufacturerDao.delete(kia.getId());
    }
}
