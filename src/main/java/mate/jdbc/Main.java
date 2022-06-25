package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer opel = new Manufacturer("Opel", "Germany");
        Manufacturer reno = new Manufacturer("Reno", "France");
        manufacturerDao.create(opel);
        manufacturerDao.create(reno);
        System.out.println(manufacturerDao.get(opel.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.delete(reno.getId()));
        System.out.println(manufacturerDao.update(reno));
    }
}
