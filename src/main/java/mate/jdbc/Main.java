package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.Dao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static Injector injector = Injector.getInstance("main.jdbc");

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");

        Dao<Manufacturer> dao = (Dao<Manufacturer>) injector.getInstance(Dao.class);
        List<Manufacturer> list = dao.getAll();
        System.out.println(list.get(0).getName());
        System.out.println(dao.get(1L));
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Kate");
        manufacturer.setCountry("Mava");
        manufacturer.setId(4L);
        dao.update(manufacturer);
        System.out.println(manufacturer.getId());
    }
}
