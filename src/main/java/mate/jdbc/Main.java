package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        // test method getAll()
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        System.out.println("All: ");
        manufacturerDao.getAll().forEach(System.out::println);

        // test method create()
        Manufacturer manufacturerBmw = new Manufacturer();
        manufacturerBmw.setName("BMW");
        manufacturerBmw.setCountry("Germany");
        manufacturerDao.create(manufacturerBmw);

        System.out.println("After create :");
        manufacturerDao.getAll().forEach(System.out::println);

        // test method delete()
        manufacturerDao.delete(manufacturerBmw.getId());
        System.out.println("After deleted :");
        manufacturerDao.getAll().forEach(System.out::println);

        // test method update()
        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setName("SAIC");
        updateManufacturer.setCountry("China");
        manufacturerDao.create(updateManufacturer);
        //rename
        updateManufacturer.setName("Saic");
        manufacturerDao.update(updateManufacturer);

        System.out.println("After updated :");
        manufacturerDao.getAll().forEach(System.out::println);

        //test method get()
        Manufacturer manufacturer = manufacturerDao.get(updateManufacturer.getId()).get();
        System.out.println(manufacturer);
    }
}
