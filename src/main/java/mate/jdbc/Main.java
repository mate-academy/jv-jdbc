package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Zelensky's inc.");
        manufacturer.setCountry("Ukraine");
        Manufacturer identifiedManufacturer = manufacturerDao.create(manufacturer);
        System.out.println("identifiedManufacturer = "
                + identifiedManufacturer);
        System.out.println("gotManufacturer = "
                + manufacturerDao.get(identifiedManufacturer.getId()));
        manufacturerDao.getAll().forEach(System.out::println);

        identifiedManufacturer.setName("Dmitro's inc.");
        identifiedManufacturer.setCountry("Belarus");
        System.out.println("updatedManufacturer = "
                + manufacturerDao.update(identifiedManufacturer));
        System.out.println("deletion completed = "
                + manufacturerDao.delete(identifiedManufacturer.getId()));
        manufacturerDao.getAll().forEach(System.out::println);
    }
}