package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer zaz = manufacturerDao.create(new Manufacturer("Zaz", "Ukraine"));
        Manufacturer dacia = manufacturerDao.create(new Manufacturer("Dacia", "Romania"));
        Manufacturer seat = manufacturerDao.create(new Manufacturer("Seat", "Spain"));
        System.out.println(manufacturerDao.update(zaz));
        System.out.println(manufacturerDao.get(dacia.getId()));
        System.out.println(manufacturerDao.delete(seat.getId()));
        System.out.println(manufacturerDao.getAll());
    }
}
