package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer apple = new Manufacturer("Apple", "USA");
        Manufacturer samsung = new Manufacturer("Samsung", "South Korea");
        Manufacturer huawei = new Manufacturer("Huawei", "China");

        manufacturerDao.create(apple);
        manufacturerDao.create(samsung);
        manufacturerDao.create(huawei);

        System.out.println(manufacturerDao.getAll());

        apple.setCountry("United States of America");
        System.out.println(manufacturerDao.update(apple));

        Optional<Manufacturer> samsungOptional = manufacturerDao.get(samsung.getId());
        System.out.println(samsungOptional.get());

        System.out.println(manufacturerDao.delete(huawei.getId()));
    }
}
