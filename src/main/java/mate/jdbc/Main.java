package mate.jdbc;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mate.jdbc.dao.ManufactureDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufactureDao dao = (ManufactureDao) injector.getInstance(ManufactureDao.class);

        Manufacturer lamborghini = new Manufacturer("Lamborghini", "Italy");
        Manufacturer ferrari = new Manufacturer("Ferrari", "Italy");
        dao.create(lamborghini);
        dao.create(ferrari);
        List<Manufacturer> firstAddResult = dao.getAll();
        firstAddResult.forEach(System.out::println);

        Manufacturer scoda = new Manufacturer("Scoda", "Hungary");
        Manufacturer zaz = new Manufacturer("Zaz", "Ukraine");
        dao.create(scoda);
        dao.create(zaz);
        List<Manufacturer> secondAddResult = dao.getAll();
        secondAddResult.forEach(System.out::println);
        scoda.setCountry("Germany");
        dao.update(scoda);
        zaz.setName("ZAZ");
        dao.update(zaz);
        List<Manufacturer> updatedInfo = dao.getAll();
        updatedInfo.forEach(System.out::println);

        Optional<Manufacturer> lamborghiniOptional = dao.get(lamborghini.getId());
        Optional<Manufacturer> ferrariOptional = dao.get(ferrari.getId());
        System.out.println(Objects.equals(lamborghiniOptional.get().getId(), lamborghini.getId()));
        System.out.println(Objects.equals(ferrariOptional.get().getId(), ferrari.getId()));

        boolean zazDeleted = dao.delete(zaz.getId());
        boolean scodaDeleted = dao.delete(scoda.getId());
        System.out.println("Zaz Deleted -" + zazDeleted + " Scoda Deleted - " + scodaDeleted);
        List<Manufacturer> finalDB = dao.getAll();
        finalDB.forEach(System.out::println);
    }
}
