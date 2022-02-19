package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturerAudi = new Manufacturer();
        manufacturerAudi.setName("Audi");
        manufacturerAudi.setCountry("Germany");
        Manufacturer manufacturer = manufacturerDao.create(manufacturerAudi);
        System.out.println(manufacturer);

        Manufacturer manufacturerFord = new Manufacturer();
        manufacturerFord.setName("Ford");
        manufacturerFord.setCountry("USA");
        manufacturerDao.create(manufacturerFord);

        System.out.println(manufacturerDao.get(1L));

        manufacturerDao.getAll().forEach(System.out::println);

        Manufacturer manufacturerSuzuki = new Manufacturer();
        manufacturerSuzuki.setId(2L);
        manufacturerSuzuki.setName("Suzuki");
        manufacturerSuzuki.setCountry("Japan");
        System.out.println(manufacturerDao.update(manufacturerSuzuki));

        System.out.println(manufacturerDao.delete(2L));
    }
}
