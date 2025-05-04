package mate.jdbc;

import java.util.Optional;
import mate.dao.ManufacturerDao;
import mate.domain.Manufacturer;
import mate.jdbc.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer opel = new Manufacturer(null, "opel", "Deutschland"
        );
        Manufacturer savedOpel = manufacturerDao.create(opel);

        System.out.println(savedOpel);

        manufacturerDao.getAll().forEach(System.out::println);

        manufacturerDao.delete(savedOpel.getId());

        manufacturerDao.getAll().forEach(System.out::println);

        Optional<Manufacturer> optionalManufacturer = manufacturerDao.get((long) 3);
        Manufacturer gotFromOptional = optionalManufacturer.get();
        System.out.println(gotFromOptional.getName());

        gotFromOptional.setCountry("Taiwan");
        manufacturerDao.update(gotFromOptional);
        manufacturerDao.getAll().forEach(System.out::println);
    }

}
