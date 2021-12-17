package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer chevrolet = new Manufacturer();
        chevrolet.setCountry("USA");
        chevrolet.setName("Chevrolet");

        Manufacturer mercedes = new Manufacturer();
        mercedes.setCountry("Germany");
        mercedes.setName("Mercedes");

        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);

        manufacturerDao.create(chevrolet);
        manufacturerDao.create(mercedes);

        System.out.println(manufacturerDao.get(1L));

        manufacturerDao.getAll().forEach(System.out::println);

        chevrolet.setCountry("America");
        manufacturerDao.update(chevrolet);

        manufacturerDao.delete(1L);
    }
}
