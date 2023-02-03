package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {

        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer firstManufacturer = new Manufacturer();
        firstManufacturer.setName("Ford");
        firstManufacturer.setCountry("USA");

        manufacturerDao.create(firstManufacturer);

        Manufacturer secondManufacturer = new Manufacturer();
        secondManufacturer.setName("Honda");
        secondManufacturer.setCountry("Japan");

        manufacturerDao.create(secondManufacturer);

        System.out.println(manufacturerDao.get(secondManufacturer.getId()));
        manufacturerDao.update(secondManufacturer);
        manufacturerDao.delete(firstManufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
