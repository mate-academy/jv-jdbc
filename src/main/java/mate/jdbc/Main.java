package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer =
                manufacturerDao.create(new Manufacturer("BMW", "Germany"));
        manufacturerDao.create(new Manufacturer("Volkswagen", "Germany"));
        manufacturerDao.create(new Manufacturer("Ford", "USA"));
        long id = manufacturer.getId();
        System.out.println("Returned manufacturer object from DB by id:"
                + id + "\n"
                + manufacturerDao.get(2L));
        manufacturer.setName("Honda");
        manufacturer.setCountry("Japan");
        manufacturer.setId(id);
        manufacturerDao.update(manufacturer);
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        System.out.println("Updated database:");
        manufacturers.forEach(System.out::println);
        manufacturerDao.delete(id);
        System.out.println("Database after delete method:");
        manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
    }
}
