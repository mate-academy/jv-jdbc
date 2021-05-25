package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer apple = new Manufacturer();
        apple.setName("Apple");
        apple.setCountry("USA");

        Manufacturer samsung = new Manufacturer();
        samsung.setName("Samsung");
        samsung.setCountry("Korea");

        Manufacturer xiaomi = new Manufacturer();
        xiaomi.setName("Xiaomi");
        xiaomi.setCountry("China");

        Manufacturer huawei = new Manufacturer();
        huawei.setName("Huawei");
        huawei.setCountry("China");

        Manufacturer lenovo = new Manufacturer();
        lenovo.setName("lenovo");
        lenovo.setCountry("China");

        //Create
        manufacturerDao.create(apple);

        //Delete
        manufacturerDao.delete(apple.getId());

        //Create
        manufacturerDao.create(samsung);
        manufacturerDao.create(xiaomi);

        //Read
        System.out.println("Get Xiaomi " + manufacturerDao
                .get(xiaomi.getId()).orElse(xiaomi));

        //Update
        samsung.setCountry("German");
        manufacturerDao.update(samsung);
        System.out.println("Updated Samsung " + samsung);

        //Create
        manufacturerDao.create(huawei);
        manufacturerDao.create(lenovo);

        //Update
        huawei.setCountry("Italy");
        manufacturerDao.update(huawei);
        System.out.println("Updated Huawei " + huawei);

        //Delete
        manufacturerDao.delete(lenovo.getId());

        //Read
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Get Huawei " + manufacturerDao.get(huawei.getId()).orElse(huawei));
    }
}
