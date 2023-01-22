package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerUsa = new Manufacturer();
        manufacturerUsa.setName("JEEP");
        manufacturerUsa.setCountry("USA");
        System.out.println(manufacturerDao.create(manufacturerUsa));
        Manufacturer manufacturerGermany = new Manufacturer();
        manufacturerGermany.setName("BMW");
        manufacturerGermany.setCountry("GERMANY");
        System.out.println(manufacturerDao.create(manufacturerGermany));
        System.out.println(manufacturerDao.get(1L));
        Manufacturer manufacturerSweden = new Manufacturer();
        manufacturerSweden.setId(1L);
        manufacturerSweden.setName("VOLVO");
        manufacturerSweden.setCountry("SWEDEN");
        System.out.println(manufacturerDao.update(manufacturerSweden));
        System.out.println(manufacturerDao.delete(1L));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
