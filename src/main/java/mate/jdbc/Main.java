package mate.jdbc;

import java.util.ArrayList;
import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(new Manufacturer("Bob", "France"));
        manufacturers.add(new Manufacturer("Bobby", "British"));
        manufacturers.add(new Manufacturer("Bober", "Italy"));
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        for (Manufacturer manufacturer: manufacturers) {
            manufacturerDao.create(manufacturer);
        }
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer manufacturer = manufacturerDao.get(manufacturers.stream()
                        .findFirst().get().getId()).orElseThrow();
        System.out.println(manufacturer);
        manufacturer.setName("Adolf");
        manufacturer.setCountry("Germany");
        manufacturerDao.update(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(manufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
