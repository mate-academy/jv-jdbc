package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer newManufacturer = new Manufacturer();
        newManufacturer.setName("msa");
        newManufacturer.setCountry("japan");

        manufacturerDao.getAll().forEach(System.out::println);

        manufacturerDao.create(newManufacturer);
        System.out.println(System.lineSeparator());
        manufacturerDao.getAll().forEach(System.out::println);

        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setId(newManufacturer.getId());
        updateManufacturer.setName("msi");
        updateManufacturer.setCountry("taiwan");
        manufacturerDao.update(updateManufacturer);
        System.out.println(System.lineSeparator());
        manufacturerDao.getAll().forEach(System.out::println);

        manufacturerDao.delete(newManufacturer.getId());
        System.out.println(System.lineSeparator());
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println(manufacturerDao.get(1L));
    }
}
