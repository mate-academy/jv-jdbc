package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerMercedes = new Manufacturer();
        manufacturerMercedes.setName("Mercedes");
        manufacturerMercedes.setCountry("German");
        System.out.println(manufacturerDao.create(manufacturerMercedes));

        System.out.println(manufacturerDao.get(8L));

        manufacturerDao.getAll().forEach(System.out::println);

        Manufacturer manufacturerOpel = new Manufacturer();
        manufacturerOpel.setId(1);
        manufacturerOpel.setName("Opel");
        manufacturerOpel.setCountry("German");
        System.out.println(manufacturerDao.update(manufacturerOpel));

        System.out.println(manufacturerDao.delete(2L));
    }
}
