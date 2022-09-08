package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer chery = new Manufacturer();
        chery.setName("Chery");
        chery.setCountry("China");
        Manufacturer ford = new Manufacturer();
        ford.setName("Ford");
        ford.setCountry("USA");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.create(chery);
        manufacturerDao.create(ford);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturer : manufacturers) {
            System.out.println(manufacturer);
        }
        chery = manufacturers.get(0);
        chery.setCountry("China");
        manufacturerDao.update(chery);
        System.out.println(manufacturerDao.get(chery.getId()));
        manufacturerDao.delete(chery.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
