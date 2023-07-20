package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer ford = new Manufacturer("Ford", "USA");
        Manufacturer mustang = new Manufacturer("Mustang", "USA");
        Manufacturer hyundai = new Manufacturer("Hyundai", "Korea");
        ford = manufacturerDao.create(ford);
        mustang = manufacturerDao.create(mustang);
        hyundai = manufacturerDao.create(hyundai);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.get(ford.getId()));
        System.out.println(manufacturerDao.delete(mustang.getId()));
        System.out.println(manufacturerDao.get(mustang.getId()));
        hyundai.setCountry("Japan");
        System.out.println(manufacturerDao.update(hyundai));
    }
}
