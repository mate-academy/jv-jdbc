package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        System.out.println(manufacturerDao.create(Manufacturer.of("AUDI", "Germany")));

        manufacturerDao.get(2L).ifPresent(System.out::println);

        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println(manufacturerDao.update(Manufacturer.of(8L, "Vepr", "Ukraine")));

        System.out.println(manufacturerDao.delete(4L));
    }
}
