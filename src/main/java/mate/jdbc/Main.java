package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector
            .getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerMazda = new Manufacturer("Mazda", "Japan");
        Manufacturer manufacturerLanos = new Manufacturer("Lanos", "Ukraine");
        manufacturerDao.create(manufacturerMazda);
        manufacturerDao.create(manufacturerLanos);
        manufacturerDao.delete(manufacturerLanos.getId());
        manufacturerMazda.setCountry("UK");
        manufacturerMazda.setName("Honda");
        manufacturerDao.update(manufacturerMazda);
        manufacturerDao.getAll().forEach(System.out::println);

    }
}
