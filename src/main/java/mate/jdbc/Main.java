package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer firstManufacturer = new Manufacturer("Peugeot", "France");
        Manufacturer secondManufacturer = new Manufacturer("Volkswagen", "Germany");
        manufacturerDao.create(firstManufacturer);
        manufacturerDao.create(secondManufacturer);

        System.out.println(manufacturerDao.get(firstManufacturer.getId()));
        System.out.println(manufacturerDao.get(secondManufacturer.getId()));
        firstManufacturer.setName("Renault");

        manufacturerDao.update(firstManufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.delete(secondManufacturer.getId());
    }
}
