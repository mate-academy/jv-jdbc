package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Renault");
        manufacturer1.setCountry("France");
        Manufacturer manufacturer1Ret = manufacturerDao.create(manufacturer1);
        System.out.println("Method create returned manufacturer " + manufacturer1Ret);

        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("Renault");
        manufacturer2.setCountry("France");
        Manufacturer manufacturer2Ret = manufacturerDao.create(manufacturer2);
        System.out.println("Method create returned manufacturer " + manufacturer2Ret);

        Manufacturer manufacturer3 = new Manufacturer();
        manufacturer3.setName("Renault");
        manufacturer3.setCountry("France");
        Manufacturer manufacturer3Ret = manufacturerDao.create(manufacturer3);
        System.out.println("Method create returned manufacturer " + manufacturer3Ret);

        System.out.println("Calling method getAll()...");
        manufacturerDao.getAll().forEach(System.out::println);

        boolean result = manufacturerDao.delete(3L);
        System.out.println("Method delete returned " + result);

        System.out.println("Calling method getAll()...");
        manufacturerDao.getAll().forEach(System.out::println);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Totota");
        manufacturer.setCountry("Japan");
        manufacturer.setId(12L);
        Manufacturer returnedManuf = manufacturerDao.update(manufacturer);
        System.out.println("Method update returned manufacturer " + returnedManuf);

        System.out.println("Calling method getAll()...");
        manufacturerDao.getAll().forEach(System.out::println);

        Optional<Manufacturer> manufacturerOptional = manufacturerDao.get(2L);
        System.out.println("Method get(2L) returning manufacturer ");
        manufacturerOptional.ifPresent(System.out::println);
    }
}
