package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer bohdan = new Manufacturer();
        bohdan.setName("Bohdan");
        bohdan.setCountry("Ukraine");
        System.out.println(manufacturerDao.create(bohdan));
        Manufacturer mercedes = new Manufacturer();
        mercedes.setName("Mercedes");
        mercedes.setCountry("Germany");
        System.out.println(manufacturerDao.create(mercedes));
        Manufacturer mitsubishi = new Manufacturer();
        mercedes.setName("Mitsubishi");
        mercedes.setCountry("Japan");
        System.out.println(manufacturerDao.create(mitsubishi));
        System.out.println(manufacturerDao.delete(2L));
        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setId(3L);
        updateManufacturer.setName("Mazda");
        updateManufacturer.setCountry("Japan");
        manufacturerDao.update(updateManufacturer);

        System.out.println(manufacturerDao.get(1L));

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
