package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        //create
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Ferrari");
        manufacturer.setCountry("Italy");
        manufacturerDao.create(manufacturer);

        //read
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(1L));

        //update
        manufacturer.setName("Tavria");
        manufacturer.setCountry("Ukraine");
        manufacturer.setId(4L);
        manufacturerDao.update(manufacturer);

        //delete
        manufacturerDao.delete(5L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
