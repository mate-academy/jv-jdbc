package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final Long UPDATE_INDEX = 2L;
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        List<Manufacturer> manufacturerList =
                List.of(new Manufacturer("ZHIGUNI", "USSR"),
                        new Manufacturer("TOYOTA", "Japane"),
                        new Manufacturer("MERCEDES", "Germany"));
        for (Manufacturer manufacturer : manufacturerList) {
            manufacturerDao.create(manufacturer);
        }
        System.out.println("created list of manufactures data");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("method getAll() was called");
        Manufacturer manufacturerByIndex = manufacturerDao.get(UPDATE_INDEX).orElseThrow();
        System.out.println("method get() was called" + manufacturerByIndex);
        manufacturerByIndex.setName("LAND-ROVER");
        Manufacturer updateManufacture = manufacturerDao.update(manufacturerByIndex);
        System.out.println("method update() was called" + updateManufacture);
        boolean deleted = manufacturerDao.delete(manufacturerByIndex.getId());
        System.out.println("method delete() was called" + deleted);
    }
}
