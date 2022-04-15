package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer audi = new Manufacturer();
        audi.setName("AUDI");
        audi.setCountry("DE");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.create(audi));
        Manufacturer bmw = new Manufacturer();
        bmw.setId(63L);
        bmw.setName("BMW");
        bmw.setCountry("DE");
        System.out.println(manufacturerDao.update(bmw));
        System.out.println(manufacturerDao.get(62L));
        System.out.println(manufacturerDao.delete(60L));
        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        for (Manufacturer manufacturers : allManufacturers) {
            System.out.println(manufacturers);
        }
    }
}
