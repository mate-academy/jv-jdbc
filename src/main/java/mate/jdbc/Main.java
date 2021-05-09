package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer audi = new Manufacturer();
        audi.setCountry("Germany");
        audi.setName("Audi");

        Manufacturer nissan = new Manufacturer();
        nissan.setCountry("Japan");
        nissan.setName("Nissan");

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        System.out.println(manufacturerDao.create(audi));
        nissan.setId(audi.getId());
        System.out.println(manufacturerDao.update(nissan));
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println(manufacturerDao.get(nissan.getId()).get());

        System.out.println(manufacturerDao.delete(nissan.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
