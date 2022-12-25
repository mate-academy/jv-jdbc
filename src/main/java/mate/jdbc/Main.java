package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao = (ManufacturerDao) injector
            .getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer manufacturerExample = new Manufacturer();
        manufacturerExample.setName("TaxiGroup");
        manufacturerExample.setCountry("Ukraine");

        Manufacturer manufacturerExample2 = new Manufacturer();
        manufacturerExample2.setName("Taxi");
        manufacturerExample2.setCountry("Che");
        manufacturerExample2.setId(1L);

        manufacturerDao.create(manufacturerExample);

        manufacturerDao.get(1L);

        manufacturerDao.update(manufacturerExample2);

        manufacturerDao.delete(1L);

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
