package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerService =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer audiManufacturer = new Manufacturer("Audi", "Germany");
        Manufacturer infinityManufacturer = new Manufacturer("Infinity", "Japan");
        Manufacturer teslaManufacturer = new Manufacturer("Tesla", "Tesla");

        System.out.println(manufacturerService.create(audiManufacturer) + System.lineSeparator()
                + manufacturerService.create(infinityManufacturer) + System.lineSeparator()
                + manufacturerService.create(teslaManufacturer) + System.lineSeparator());

        System.out.println(manufacturerService.get(16L) + System.lineSeparator()
                + manufacturerService.get(999L));

        infinityManufacturer.setName("Nissan");
        System.out.println(manufacturerService.update(infinityManufacturer));

        System.out.println(manufacturerService.delete(36L));
        manufacturerService.getAll().forEach(System.out::println);
    }
}
