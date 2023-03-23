package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        injector.getInstance(ManufacturerDao.class);
        Manufacturer porsche = manufacturerDao.create(new Manufacturer("Porsche","Germany"));
        Manufacturer bmw = manufacturerDao.create(new Manufacturer("BMW","Germany"));
        Manufacturer hyundai = manufacturerDao.create(new Manufacturer("Hyundai", "South Korea"));
        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(3L));
        manufacturerDao.update(new Manufacturer(3L,"Peugeot","France"));
        manufacturerDao.delete(2L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}

