package mate.jdbc;

import mate.jdbc.dao.Dao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {

    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId((long) 3);
        manufacturer.setName("test2");
        manufacturer.setCountry("country2");

        Dao<Manufacturer> manufacturerDao = (Dao<Manufacturer>) injector.getInstance(Dao.class);
        System.out.println(manufacturerDao.delete(-2));
        Manufacturer update = manufacturerDao.update(manufacturer);
        System.out.println(update);
        for (Manufacturer element : manufacturerDao.getAll()) {
            System.out.println(element.toString());
        }
        System.out.println(manufacturerDao.get(-2));
    }
}
