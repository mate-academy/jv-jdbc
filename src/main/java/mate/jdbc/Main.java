package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao
                = (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);

        // TESTING CREATE METHOD
        Manufacturer manufacturerFromUsa = new Manufacturer();
        manufacturerFromUsa.setName("first_name");
        manufacturerFromUsa.setCountry("USA");
        Manufacturer createdManufacturerFromUsa = manufacturerDao
                .create(manufacturerFromUsa);
        System.out.println(createdManufacturerFromUsa);

        // TESTING UPDATE METHOD
        Manufacturer manufacturerFromSpain = new Manufacturer();
        manufacturerFromSpain.setName("second_name");
        manufacturerFromSpain.setCountry("Spain");
        manufacturerFromSpain.setId(3L);
        Manufacturer updatedManufacturer = manufacturerDao
                .update(manufacturerFromSpain);
        System.out.println(updatedManufacturer);

        // TESTING CREATE METHOD
        Manufacturer manufacturerFromUkraine = new Manufacturer();
        manufacturerFromUkraine.setName("third_name");
        manufacturerFromUkraine.setCountry("Ukraine");
        Manufacturer createdManufacturerFromUkraine = manufacturerDao
                .create(manufacturerFromUkraine);
        System.out.println(createdManufacturerFromUkraine);

        // TESTING GET METHOD
        // I was playing with DB before and this is the reason why I specify the identifier 3
        Optional<Manufacturer> optionalManufacturerFromSpain = manufacturerDao.get(3L);
        Manufacturer manufacturerFromGetMethod = optionalManufacturerFromSpain.get();
        System.out.println(manufacturerFromGetMethod);

        // TESTING CREATE METHOD
        Manufacturer manufacturerFromPoland = new Manufacturer();
        manufacturerFromUkraine.setName("fourth_name");
        manufacturerFromUkraine.setCountry("Poland");
        Manufacturer createdManufacturerFromPoland = manufacturerDao
                .create(manufacturerFromPoland);
        System.out.println(createdManufacturerFromPoland);

        // TESTING DELETE METHOD
        boolean isManufacturerFromPolandDeleted = manufacturerDao.delete(5L);
        System.out.println(isManufacturerFromPolandDeleted);

        // TESTING GET ALL METHOD
        List<Manufacturer> all = manufacturerDao.getAll();
        all.forEach(System.out::println);
    }
}
