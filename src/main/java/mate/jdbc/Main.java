package mate.jdbc;

import mate.jdbc.model.*;

public class Main {
    //private static final Injector injector = Injector.getInstance("YOUR_PACKAGE");
    //hw-jv-jdbc

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("GM");

        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Manufacturer savedManufacturers = manufacturerDao.create(manufacturer);
        System.out.println(savedManufacturers);
        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println(manufacturerDao.delete(savedManufacturers.getId()));

        //allFormats.iterator();  iterator in for



        /*ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        // initialize field values using setters or constructor
        manufacturerDao.create(manufacturer);
        // test other methods from ManufacturerDao
*/



    }
}
