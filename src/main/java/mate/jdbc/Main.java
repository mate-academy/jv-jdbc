package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer volkswagen = manufacturerDao
                .create(new Manufacturer("Volkswagen", "Germany"));
        Manufacturer skoda = manufacturerDao
                .create(new Manufacturer("Skoda", "Czech Republic"));
        Manufacturer hyundai = manufacturerDao
                .create(new Manufacturer("Hyundai", "Republic of Korea"));
        manufacturerDao.getAll().forEach(System.out::println);
        hyundai.setCountry("Korea");
        manufacturerDao.update(hyundai);
        System.out.println(manufacturerDao.get(3L));
        System.out.println(manufacturerDao.delete(1L));
    }
}
