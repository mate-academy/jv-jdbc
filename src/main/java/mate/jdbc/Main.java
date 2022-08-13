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
        manufacturerList.add(new Manufacturer("SONY", "Japan"));
        manufacturerList.add(new Manufacturer("Apple", "USA"));
        System.out.println(">>test => create");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerList
                .forEach(manufacturer -> {
                    manufacturerDao.create(manufacturer);
                    System.out.println(manufacturer);
                });
        System.out.println(">>test => get");
        Long existId = manufacturerList.get(0).getId();
        Long notExistId = existId + 10;
        System.out.println(manufacturerDao.get(existId));
        System.out.println(manufacturerDao.get(notExistId));
        System.out.println(">>test => getAll");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(">>test => update");
        manufacturerDao.getAll()
                .forEach(manufacturer -> {
                    manufacturer.setName(manufacturer.getName().toUpperCase());
                    System.out.println(manufacturerDao.update(manufacturer));
                });
        System.out.println(">>test => delete");
        System.out.println(manufacturerDao.delete(existId));
        System.out.println(manufacturerDao.delete(notExistId));

    }
}
