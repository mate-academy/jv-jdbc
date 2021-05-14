package mate.jdbc;

import java.util.Objects;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(
                ManufacturerDao.class);

        Manufacturer manufacturerMersedes = new Manufacturer();
        manufacturerMersedes.setName("Mersedes");
        manufacturerMersedes.setCountry("Germany");
        manufacturerDao.create(manufacturerMersedes);
        Manufacturer manufacturerLexus = new Manufacturer();
        manufacturerMersedes.setName("Lexus");
        manufacturerMersedes.setCountry("Japan");
        manufacturerDao.create(manufacturerLexus);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.delete(manufacturerMersedes.getId()));

        Manufacturer manufacturerTesla = new Manufacturer();
        manufacturerTesla.setName("Tesla");
        manufacturerTesla.setCountry("USA");
        manufacturerDao.create(manufacturerTesla);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerTesla.setCountry("USA, California");
        manufacturerDao.update(manufacturerTesla);
        manufacturerDao.getAll().forEach(System.out::println);

        Optional<Manufacturer> lexusOptional = manufacturerDao.get(manufacturerLexus.getId());
        System.out.println(Objects.equals(lexusOptional.get().getId(),
                manufacturerMersedes.getId()));

        boolean teslaDeleted = manufacturerDao.delete(manufacturerTesla.getId());
        System.out.println("Tesla deleted" + teslaDeleted);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
