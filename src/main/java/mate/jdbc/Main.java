package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer firstManufacturer = new Manufacturer();
        firstManufacturer.setName("Lexus");
        firstManufacturer.setCountry("USA");

        manufacturerDao.create(firstManufacturer);

        Manufacturer secondManufacturer = new Manufacturer();
        secondManufacturer.setName("Audi");
        secondManufacturer.setCountry("Germany");

        manufacturerDao.create(secondManufacturer);

        System.out.println(manufacturerDao.get(11L));
        manufacturerDao.update(secondManufacturer);
        manufacturerDao.delete(10L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
