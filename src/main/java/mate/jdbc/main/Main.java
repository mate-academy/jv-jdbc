package mate.jdbc.main;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerMercedes = new Manufacturer("Mercedes", "GERMANY");
        Manufacturer manufacturerHonda = new Manufacturer("HONDA", "JAPAN");
        Manufacturer manufacturerZaz = new Manufacturer("ZAZ", "UKRAINE");
        Manufacturer manufacturerKraz = new Manufacturer("KRAZ", "UKRAINE");
        Manufacturer manufacturerUpdate = new Manufacturer(17L, "AUDI", "GERMANY");

        manufacturerDao.create(manufacturerHonda);
        manufacturerDao.create(manufacturerZaz);
        manufacturerDao.create(manufacturerKraz);
        manufacturerDao.update(manufacturerUpdate);
        manufacturerDao.delete(20L);
        System.out.println(manufacturerDao.get(20L));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
