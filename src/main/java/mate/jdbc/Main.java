package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static Manufacturer manufacturer;
    private static final List<Manufacturer> manufacturers = new ArrayList<>();

    static {
        for (int i = 0; i < 3; i++) {
            manufacturer = new Manufacturer();
            manufacturer.setName("Bobby " + i);
            manufacturer.setCountry("Ukraine " + i);
            manufacturers.add(manufacturer);
        }
    }

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer manufacturer = manufacturerDao.get(manufacturers.get(0).getId()).orElseThrow();
        System.out.println(manufacturer);
        manufacturer.setName("Adolf");
        manufacturer.setCountry("Germany");
        manufacturerDao.update(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(manufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
