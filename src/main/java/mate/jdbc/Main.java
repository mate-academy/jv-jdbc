package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");
    private static final ManufacturerDao manufacturerDao = (ManufacturerDao) injector
            .getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer manufacturer1 = manufacturerDao.create(new Manufacturer("BMW", "Germany"));
        manufacturerDao.create(new Manufacturer("Volkswagen", "Germany"));
        manufacturerDao.create(new Manufacturer("Ford", "USA"));
        System.out.println("Returned manufacturer object from DB by id:"
                + manufacturer1.getId() + "\n"
                + manufacturerDao.get(manufacturer1.getId()));
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        System.out.println("Method \"getAll()\" was called:");
        manufacturers.forEach(System.out::println);
        Manufacturer manufacturer2 = new Manufacturer("Honda", "Japan");
        manufacturer2.setId(2);
        manufacturerDao.update(manufacturer2);
        manufacturers = manufacturerDao.getAll();
        System.out.println("Updated database:");
        manufacturers.forEach(System.out::println);
        manufacturerDao.delete(manufacturer1.getId());
        System.out.println("Database after delete method by id:"
                + manufacturer1.getId());
        manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
    }
}
