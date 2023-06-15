package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.module.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer("BMW", "Germany");
        System.out.println("Create manufacturer BMW:  ");
        ManufacturerDao manufacturerDao = (ManufacturerDao)injector
                .getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.create(manufacturer));

        System.out.println("get manufacturer BMW:  ");
        System.out.println(manufacturerDao.get(manufacturer.getId()));

        System.out.println("Update manufacturer BMW to Mercedes:  ");
        manufacturer.setName("Mercedes");
        System.out.println(manufacturerDao.update(manufacturer));

        System.out.println("Create manufacturer ferrari:  ");
        Manufacturer ferrariManufacturer = new Manufacturer("ferrari", "Italy");

        System.out.println(manufacturerDao.create(ferrariManufacturer));
        System.out.println("Get all manufacturer: ");
        System.out.println(manufacturerDao.getAll());

        System.out.println("Delete manufacturer ferrari: ");
        System.out.println(manufacturerDao.delete(ferrariManufacturer.getId()));

        System.out.println("Get all manufacturer: ");
        System.out.println(manufacturerDao.getAll());
    }
}
