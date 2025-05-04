package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)injector
                    .getInstance(ManufacturerDao.class);
        Manufacturer bucketWithBolts = new Manufacturer("ZAZ", "Ukraine");
        Manufacturer luxury = new Manufacturer("Maserati", "Italy");
        Manufacturer solidity = new Manufacturer("Toyota Motor Corporation", "Japan");
        Manufacturer janky = new Manufacturer("GM", "USA");
        manufacturerDao.create(bucketWithBolts);
        manufacturerDao.create(luxury);
        manufacturerDao.create(solidity);
        manufacturerDao.create(janky);
        System.out.println(manufacturerDao.getAll().toString());
        manufacturerDao.delete(4L);
        bucketWithBolts.setName("VAZ");
        bucketWithBolts.setCountry("russia");
        manufacturerDao.update(bucketWithBolts);
        System.out.println(manufacturerDao.getAll().toString());
    }
}
