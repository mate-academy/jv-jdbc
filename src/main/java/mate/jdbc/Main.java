package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer apple = new Manufacturer();
        apple.setName("Apple");
        apple.setCountry("United States");

        Manufacturer lenovo = new Manufacturer();
        lenovo.setName("Lenovo");
        lenovo.setCountry("China");

        Manufacturer xiaomi = new Manufacturer();
        xiaomi.setName("Xiaomi");
        xiaomi.setCountry("China");

        System.out.println(".....Save manufacturers to DB.....");
        System.out.printf("Apple manufacturer saved to DB %s%n",
                manufacturerDao.create(apple));
        System.out.printf("Lenovo manufacturer saved to DB %s%n",
                manufacturerDao.create(lenovo));
        System.out.printf("Xiaomi manufacturer saved to DB %s%n",
                manufacturerDao.create(xiaomi));
        System.out.println();

        System.out.println(".....Remove manufacturer from DB.....");
        System.out.println(manufacturerDao.delete(xiaomi.getId()));
        System.out.println();

        System.out.println(".....Show all data from DB.....");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println();

        System.out.println(".....Update manufacturer in DB.....");
        apple.setName("Samsung");
        System.out.printf("New element in DB %s%n", manufacturerDao.update(apple));
        System.out.println();

        System.out.println("....Get manufacturer from DB.....");
        System.out.println(manufacturerDao.get(apple.getId()));
    }
}
