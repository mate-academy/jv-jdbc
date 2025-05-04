package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer zaz = new Manufacturer();
        zaz.setCountry("Ukraine");
        zaz.setName("ZAZ");
        Manufacturer bogdan = new Manufacturer();
        bogdan.setCountry("Ukraine");
        bogdan.setName("Bogdan");
        Manufacturer kraz = new Manufacturer();
        kraz.setCountry("Ukraine");
        kraz.setName("KrAZ");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(zaz);
        manufacturerDao.create(bogdan);
        manufacturerDao.create(kraz);
        System.out.println(manufacturerDao.get(kraz.getId()).get());
        Manufacturer updateKraz = new Manufacturer(kraz.getId(), "KrUSA", "USA");
        System.out.println(manufacturerDao.update(updateKraz));
        System.out.println(manufacturerDao.delete(updateKraz.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
