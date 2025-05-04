package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        //insert two
        Manufacturer toyotaManufacturer = new Manufacturer("Toyota", "Japan");
        Manufacturer gmcManufacturer = new Manufacturer("Chevrolet", "USA");
        manufacturerDao.create(toyotaManufacturer);
        manufacturerDao.create(gmcManufacturer);
        System.out.println(System.lineSeparator() + "both after insert");//get all
        System.out.println(manufacturerDao.get(toyotaManufacturer.getId()));
        System.out.println(manufacturerDao.get(gmcManufacturer.getId()));
        System.out.println(System.lineSeparator() + "all after change");//change one, get all
        toyotaManufacturer.setName("Lexus");
        manufacturerDao.update(toyotaManufacturer);
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(System.lineSeparator() + "all after delete:");//delete one, get all
        manufacturerDao.delete(gmcManufacturer.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
