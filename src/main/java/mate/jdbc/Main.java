package mate.jdbc;

import java.util.Objects;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao dao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer lamborghini = new Manufacturer("Lamborghini", "Italy");
        Manufacturer ferrari = new Manufacturer("Ferrari", "Italy");
        dao.create(lamborghini);
        dao.create(ferrari);
        dao.getAll().forEach(System.out::println);

        Manufacturer scoda = new Manufacturer("Scoda", "Hungary");
        Manufacturer zaz = new Manufacturer("Zaz", "Ukraine");
        dao.create(scoda);
        dao.create(zaz);
        dao.getAll().forEach(System.out::println);
        scoda.setCountry("Germany");
        dao.update(scoda);
        zaz.setName("ZAZ");
        dao.update(zaz);
        dao.getAll().forEach(System.out::println);

        Optional<Manufacturer> lamborghiniOptional = dao.get(lamborghini.getId());
        Optional<Manufacturer> ferrariOptional = dao.get(ferrari.getId());
        System.out.println(Objects.equals(lamborghiniOptional.get().getId(), lamborghini.getId()));
        System.out.println(Objects.equals(ferrariOptional.get().getId(), ferrari.getId()));

        boolean zazDeleted = dao.delete(zaz.getId());
        boolean scodaDeleted = dao.delete(scoda.getId());
        System.out.println("Zaz Deleted - " + zazDeleted + " Scoda Deleted - " + scodaDeleted);
        dao.getAll().forEach(System.out::println);
    }
}
