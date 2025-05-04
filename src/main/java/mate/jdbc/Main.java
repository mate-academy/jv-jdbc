package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.Dao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Dao<Manufacturer> dao = (Dao<Manufacturer>)
                injector.getInstance(Dao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Kate");
        manufacturer.setCountry("Mava");
        dao.create(manufacturer);
        manufacturer.setName("Vova");
        dao.update(manufacturer);

        List<Manufacturer> list = dao.getAll();
        list.forEach(m -> System.out.println(m.getName()));
        System.out.println(manufacturer.getId());
        dao.delete(manufacturer.getId());
    }
}
