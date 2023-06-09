package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector
            .getInstance("mate.jdbc");
    private static final ManufacturerDao manufacturerDao =
            (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer toyotaManufacturer = new Manufacturer("Toyota", "Japan");
        Manufacturer fordManufacturer = new Manufacturer("Ford", "Usa");

        System.out.println("Create manufacturers Toyota and Ford");
        System.out.println(manufacturerDao.create(toyotaManufacturer));
        System.out.println(manufacturerDao.create(fordManufacturer));

        System.out.println("Get manufacturer Ford");
        System.out.println(manufacturerDao.get(fordManufacturer.getId()));

        System.out.println("Update manufacturer Toyota to JT");
        toyotaManufacturer.setName("JT");
        System.out.println(manufacturerDao.update(toyotaManufacturer));

        System.out.println("Get all manufacturer");

        var manufacturers = manufacturerDao.getAll();
        for (Manufacturer man : manufacturers) {
            System.out.println(man);
        }

        System.out.println("Delete Ford manufacturer");
        System.out.println(manufacturerDao.delete(fordManufacturer.getId()));

        System.out.println("Get all manufacturer");
        System.out.println(manufacturerDao.getAll());
    }
}
