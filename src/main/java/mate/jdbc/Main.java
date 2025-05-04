package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturerDao.create(new Manufacturer("Ford", "USA"));
        manufacturerDao.create(new Manufacturer("Toyota", "Japan"));
        System.out.println("1st manufacturer from DB: " + manufacturerDao.get(1L));
        System.out.println("Non-existing manufacturer from DB: " + manufacturerDao.get(10L));
        manufacturerDao.create(new Manufacturer("Renault", "France"));
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        System.out.println("All manufacturers from DB after adding 3rd: " + manufacturers);
        manufacturers.get(2).setName("Volkswagen");
        manufacturers.get(2).setCountry("Germany");
        System.out.println("Successfully update 3rd manufacturer: "
                + manufacturerDao.update(manufacturers.get(2)));
        manufacturerDao.delete(3L);
        manufacturers = manufacturerDao.getAll();
        System.out.println("All manufacturers from DB after deleting 3rd: " + manufacturers);
    }
}
