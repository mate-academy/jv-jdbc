package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        System.out.println("getAll:");
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);

        System.out.println("get by id:");
        Optional<Manufacturer> manufacturerById = manufacturerDao.get(4L);
        if (manufacturerById.isPresent()) {
            System.out.println(manufacturerById.get());
        } else {
            System.out.println("null");
        }

        System.out.println("create:");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("test name");
        manufacturer.setCountry("test country");
        System.out.println(manufacturer);
        System.out.println(manufacturerDao.create(manufacturer));

        System.out.println("update");
        Manufacturer manufacturerUpdate = new Manufacturer();
        manufacturerUpdate.setId(3L);
        manufacturerUpdate.setName("Ford");
        manufacturerUpdate.setCountry("Germany");
        System.out.println(manufacturerDao.update(manufacturerUpdate));
        System.out.println(manufacturerDao.getAll());

        System.out.println("delete");
        System.out.println(manufacturerDao.delete(3L));
        System.out.println(manufacturerDao.getAll());
    }
}
