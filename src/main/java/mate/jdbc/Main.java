package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        List<Manufacturer> manufacturers = List.of(new Manufacturer("Nissan", "Japan"),
                new Manufacturer("Audi", "Germany"),
                new Manufacturer("Daewoo", "Korea"));
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        for (Manufacturer manufacturer: manufacturers) {
            manufacturerDao.create(manufacturer);
        }
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer manufacturer = manufacturerDao.get(manufacturers.stream()
                .findFirst()
                .get()
                .getId())
                .orElseThrow();
        System.out.println(manufacturer);
        manufacturer.setName("BMW");
        manufacturer.setCountry("IDK");
        manufacturerDao.update(manufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(manufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
