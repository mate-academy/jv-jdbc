package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer;

        Manufacturer mitsubishi = new Manufacturer();
        mitsubishi.setName("Mitsubishi");
        mitsubishi.setCountry("Japan");
        manufacturer = manufacturerDao.create(mitsubishi);
        System.out.println("Created first manufacturer: " + manufacturer);

        Manufacturer mersedes = new Manufacturer();
        mersedes.setName("Mersedes");
        mersedes.setCountry("German");
        manufacturer = manufacturerDao.create(mersedes);
        System.out.println("Created second manufacturer: " + manufacturer);

        Manufacturer lamborghini = new Manufacturer();
        lamborghini.setName("Lamborghini");
        lamborghini.setCountry("Italy");
        manufacturer = manufacturerDao.create(lamborghini);
        System.out.println("Created third manufacturer: " + manufacturer);

        manufacturer = manufacturerDao.get(2L).get();
        System.out.println("Manufacturer got from DB: " + manufacturer);

        manufacturer.setName("Mercedes");
        manufacturer.setCountry("Germany");
        manufacturer = manufacturerDao.update(manufacturer);
        System.out.println("Manufacturer after update: " + manufacturer);

        boolean isDeleted = manufacturerDao.delete(1L);
        System.out.println("Manufacturer deleted = " + isDeleted);

        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        manufacturers.forEach(System.out::println);
    }
}
