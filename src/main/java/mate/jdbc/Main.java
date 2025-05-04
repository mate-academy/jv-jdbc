package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerZelapa = new Manufacturer("Zelapa", "Ukraine");
        Manufacturer manufacturerShostam = new Manufacturer("Shostam", "Destam");
        manufacturerDao.create(manufacturerZelapa);
        manufacturerDao.create(manufacturerShostam);
        manufacturerDao.delete(manufacturerShostam.getId());
        manufacturerZelapa.setCountry("Country");
        manufacturerZelapa.setName("Uber");
        manufacturerDao.update(manufacturerZelapa);
        manufacturerDao.get(manufacturerZelapa.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
