package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturerZaz = new Manufacturer();
        manufacturerZaz.setCountry("Ukraine");
        manufacturerZaz.setName("ZAZ");

        Manufacturer manufacturerDaewoo = new Manufacturer();
        manufacturerDaewoo.setCountry("Ukraine");
        manufacturerDaewoo.setName("Daewoo");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        manufacturerDao.create(manufacturerZaz);
        manufacturerDao.create(manufacturerDaewoo);

        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println(manufacturerDao.delete(1L));

        System.out.println(manufacturerDao.get(2L).get());

        Manufacturer manufacturerKraz = manufacturerDao.get(2L).get();
        manufacturerKraz.setCountry("Ukraine");
        manufacturerKraz.setName("KRAZ");
        System.out.println(manufacturerDao.update(manufacturerKraz));
    }
}
