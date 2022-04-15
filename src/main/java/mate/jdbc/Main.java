package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        Manufacturer manufacturer1 = new Manufacturer();
        /*
        // initialize field values using setters or constructor
        manufacturer.setId(3L);
        manufacturer.setName("Mazda");
        manufacturer.setCountry("Japan");
        manufacturer1.setName("BMW");
        manufacturer1.setCountry("Germany");

        // test other methods from ManufacturerDao
        manufacturerDao.create(manufacturer);

        // System.out.println(manufacturerDao.get(20L));
        System.out.println(manufacturerDao.update(manufacturer).toString());
        System.out.println(manufacturerDao.create(manufacturer1).toString());
        */
        // System.out.println(manufacturerDao.delete(4L));
        System.out.println(manufacturerDao.get(1L));
        for (Manufacturer items:
                manufacturerDao.getAll()) {
            System.out.println(items.toString());
        }
    }
}
