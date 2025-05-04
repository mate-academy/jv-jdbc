package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerAudi = new Manufacturer("Audi", "Germany");
        Manufacturer manufacturerBmw = new Manufacturer("Bmw", "Germany");
        manufacturerDao.create(manufacturerAudi);
        manufacturerDao.create(manufacturerBmw);
        System.out.println(manufacturerDao.get(manufacturerAudi.getId()));
        manufacturerDao.delete(manufacturerBmw.getId());
        manufacturerAudi.setCountry("Ukraine");
        manufacturerDao.update(manufacturerAudi);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
