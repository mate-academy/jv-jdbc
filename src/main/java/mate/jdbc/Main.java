package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer("ZAZ", "Ukraine");
        System.out.println("Create manufacturer ZAZ");
        System.out.println(manufacturerDao.create(manufacturer));

        System.out.println("Get manufacturer ZAZ");
        System.out.println(manufacturerDao.get(manufacturer.getId()));

        System.out.println("Update manufacturer ZAZ to KyivAvto");
        manufacturer.setName("KyivAvto");
        System.out.println(manufacturerDao.update(manufacturer));

        System.out.println("Create manufacturer Toyota");
        Manufacturer otherManufacturer = new Manufacturer("Toyota", "Japan");
        System.out.println(manufacturerDao.create(otherManufacturer));

        System.out.println("Get all manufacturer");
        System.out.println(manufacturerDao.getAll());

        System.out.println("Delete Toyota manufacturer");
        System.out.println(manufacturerDao.delete(otherManufacturer.getId()));

        System.out.println("Get all manufacturer");
        System.out.println(manufacturerDao.getAll());
    }
}
