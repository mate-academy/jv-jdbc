package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {
        ManufacturerDao manufacturerService = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerExample = new Manufacturer();
        manufacturerExample.setName("TaxiGroup");
        manufacturerExample.setCountry("Ukraine");

        Manufacturer manufacturerExample2 = new Manufacturer();
        manufacturerExample2.setName("Taxi");
        manufacturerExample2.setCountry("Che");
        manufacturerExample2.setId(1L);

        manufacturerService.create(manufacturerExample);

        manufacturerService.get(1L);

        manufacturerService.update(manufacturerExample2);

        manufacturerService.delete(1L);

        manufacturerService.getAll().forEach(System.out::println);
    }
}
