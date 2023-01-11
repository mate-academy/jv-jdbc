package mate.jdbc;

import mate.jdbc.lib.Injector;
import mate.jdbc.lib.ManufacturerDao;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Lanos");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);
        manufacturerDao.get(manufacturer.getId());
        manufacturer.setId(8L);
        manufacturer.setName("Tavria");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(12L);
        manufacturerDao.getAll().forEach(System.out::println);

    }
}
