package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturersDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        final ManufacturersDao manufacturersDao
                = (ManufacturersDao) injector.getInstance(ManufacturersDao.class);

        //**createManufacturer
        Manufacturer manufacturerNissan = new Manufacturer();
        manufacturerNissan.setName("Nissan");
        manufacturerNissan.setCountry("Japan");
        Manufacturer manufacturerJeep = new Manufacturer();
        manufacturerJeep.setName("Jeep");
        manufacturerJeep.setCountry("USA");
        Manufacturer manufacturerJaguar = new Manufacturer();
        manufacturerJaguar.setName("Jaguar");
        manufacturerJaguar.setCountry("England");
        Manufacturer manufacturerNissanCreated = manufacturersDao.create(manufacturerNissan);
        Manufacturer manufacturerJeepCreated = manufacturersDao.create(manufacturerJeep);
        Manufacturer manufacturerJaguarCreated = manufacturersDao.create(manufacturerJaguar);
        System.out.println(manufacturerNissanCreated);
        System.out.println(manufacturerJeepCreated);
        System.out.println(manufacturerJaguarCreated);

        //**updateManufacturer
        Manufacturer manufacturerLexus = new Manufacturer();
        manufacturerLexus.setName("Lexus");
        manufacturerLexus.setCountry("Japan");
        manufacturerLexus.setId(3L);
        Manufacturer manufacturerUpdated = manufacturersDao.update(manufacturerLexus);
        System.out.println(manufacturerUpdated);

        //**deleteManufacturer
        boolean isManufacturerDeleted = manufacturersDao.delete(1L);
        System.out.println(isManufacturerDeleted);

        //**getManufacturer
        Optional<Manufacturer> manufacturerGot = manufacturersDao.get(2L);
        System.out.println(manufacturerGot);

        //**getAllManufacturers
        List<Manufacturer> allManufacturers = manufacturersDao.getAll();
        allManufacturers.forEach(System.out::println);
    }
}
