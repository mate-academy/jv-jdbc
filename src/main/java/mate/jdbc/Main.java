package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer lamborghini = new Manufacturer();
        lamborghini.setName("Lamborghini Group");
        lamborghini.setCountry("Italy");

        manufacturerDao.create(lamborghini);

        Manufacturer volkswagen = new Manufacturer();
        volkswagen.setName("Volkswagen Group");
        volkswagen.setCountry("German");
        volkswagen.setId(1L);
        manufacturerDao.update(volkswagen);

        manufacturerDao.delete(5L);

        System.out.println(manufacturerDao.getAll());

        System.out.println(manufacturerDao.get(9L));
    }
}
