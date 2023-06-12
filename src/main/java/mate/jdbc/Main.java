package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {

        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        // getAll list
        manufacturerDao.getAll().forEach(System.out::println);
        Manufacturer manufacturer = new Manufacturer();
        System.out.println("------------");
        // create new
        manufacturer.setName("Lamborghini");
        manufacturer.setCountry("Italy");
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(savedManufacturer);
        System.out.println("------------");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("------------");
        // update
        manufacturer.setName("Mercedes-Benz");
        manufacturer.setId(4L);
        manufacturer.setCountry("Germany");
        Manufacturer updateManufacturer = manufacturerDao.update(manufacturer);
        System.out.println(updateManufacturer);
        System.out.println("------------");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("------------");
        // delete
        System.out.println(manufacturerDao.delete(savedManufacturer.getId()));
        System.out.println("---------");
        // get all
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
