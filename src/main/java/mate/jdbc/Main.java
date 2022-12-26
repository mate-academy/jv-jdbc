package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Enrike");
        manufacturer.setCountry("Brazil");
        manufacturerDao.create(manufacturer);

        manufacturer.setName("Alonso");
        manufacturerDao.update(manufacturer);

        manufacturerDao.delete(22L);

        System.out.println(manufacturerDao.get(17L).get());

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
