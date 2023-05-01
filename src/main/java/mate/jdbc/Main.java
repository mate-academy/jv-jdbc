package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerA = Manufacturer.builder().name("A").country("AA").build();
        Manufacturer manufacturerB = Manufacturer.builder().name("B").country("BB").build();
        Manufacturer manufacturerC = Manufacturer.builder().name("C").country("CC").build();
        Manufacturer manufacturerD = Manufacturer.builder().name("D").country("DD").build();
        Manufacturer manufacturerE = Manufacturer.builder().name("E").country("EE").build();
        System.out.println(manufacturerDao.create(manufacturerA));
        System.out.println(manufacturerDao.create(manufacturerB));
        System.out.println(manufacturerDao.create(manufacturerC));
        System.out.println(manufacturerDao.create(manufacturerD));
        System.out.println(manufacturerDao.create(manufacturerE));
        System.out.println(manufacturerDao.get(1L));
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerE.setName("EEE");
        manufacturerE.setCountry("EEEEE");
        System.out.println(manufacturerDao.update(manufacturerE));
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(manufacturerE.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
