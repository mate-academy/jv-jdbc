package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        Manufacturer manufacturer = new Manufacturer("SONY", "Japan");
        manufacturerList.add(manufacturer);
        manufacturer = new Manufacturer("Apple", "USA");
        manufacturerList.add(manufacturer);
        System.out.println(">>test => create");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerList
                .forEach(m -> {
                    manufacturerDao.create(m);
                    System.out.println(m);
                });
        System.out.println(">>test => get");
        Long id1 = manufacturerList.get(0).getId();
        System.out.println(manufacturerDao.get(id1));
        System.out.println(manufacturerDao.get(id1 + 10));
        System.out.println(">>test => getAll");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(">>test => update");
        manufacturerDao.getAll()
                .forEach(m -> {
                    m.setName(m.getName().toUpperCase());
                    System.out.println(manufacturerDao.update(m));
                });
        System.out.println(">>test => delete");
        Long id2 = manufacturerList.get(1).getId();
        System.out.println(manufacturerDao.delete(id2));
        System.out.println(manufacturerDao.delete(id2 + 10));

    }
}
