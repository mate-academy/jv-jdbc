package mate.jdbc;

import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao = (ManufacturerDao) injector
            .getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer samsung = new Manufacturer();
        samsung.setName("Samsung");
        samsung.setCountry("Korea");

        Manufacturer apple = new Manufacturer();
        apple.setName("Apple");
        apple.setCountry("USA");

        Manufacturer xiaomi = new Manufacturer();
        xiaomi.setName("Xiaomi");
        xiaomi.setCountry("China");

        Manufacturer huawei = new Manufacturer();
        huawei.setName("Huawei");
        huawei.setCountry("China");

        manufacturerDao.create(samsung);
        manufacturerDao.create(apple);
        manufacturerDao.create(huawei);
        manufacturerDao.create(xiaomi);

        List<Manufacturer> list = manufacturerDao.getAll();
        System.out.println(list);

        Optional<Manufacturer> huaweiOptional = manufacturerDao.get(huawei.getId());
        System.out.println(huaweiOptional.get());

        apple.setCountry("California");
        System.out.println(manufacturerDao.update(apple));

        boolean isDeletedSamsung = manufacturerDao.delete(samsung.getId());
        System.out.println(isDeletedSamsung);
    }
}
