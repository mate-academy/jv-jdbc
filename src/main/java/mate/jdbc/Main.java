package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturersDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturersDao manufacturersDao = (ManufacturersDao) injector
                .getInstance(ManufacturersDao.class);

        Manufacturer manufacturerNissan = new Manufacturer();
        manufacturerNissan.setName("Nissan");
        manufacturerNissan.setCountry("Japan");
        Manufacturer manufacturerNissanCreated = manufacturersDao.create(manufacturerNissan);
        Manufacturer manufacturerJaguar = new Manufacturer();
        manufacturerJaguar.setName("Jaguar");
        manufacturerJaguar.setCountry("England");
        Manufacturer manufacturerJaguarCreated = manufacturersDao.create(manufacturerJaguar);
        System.out.println(manufacturerNissanCreated);
        System.out.println(manufacturerJaguarCreated);

        Manufacturer manufacturerLexus = new Manufacturer();
        manufacturerLexus.setName("Lexus");
        manufacturerLexus.setCountry("Japan");
        manufacturerLexus.getId();
        Manufacturer manufacturerUpdated = manufacturersDao.update(manufacturerLexus);
        System.out.println(manufacturerUpdated);

        boolean isManufacturerDeleted = manufacturersDao.delete(manufacturerJaguarCreated.getId());
        System.out.println(isManufacturerDeleted);

        Optional<Manufacturer> manufacturerGot = manufacturersDao
                .get(manufacturerNissanCreated.getId());
        System.out.println(manufacturerGot);

        List<Manufacturer> allManufacturers = manufacturersDao.getAll();
        allManufacturers.forEach(System.out::println);
    }
}
