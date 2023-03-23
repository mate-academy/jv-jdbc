package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final String NAME_OF_FIRST_MANUFACTURER = "Ferrari";
    private static final String COUNTRY_OF_FIRST_MANUFACTURER = "Italy";
    private static final String NAME_OF_SECOND_MANUFACTURER = "Mercedes";
    private static final String COUNTRY_OF_SECOND_MANUFACTURER = "Germany";
    private static final String NAME_OF_MANUFACTURER_CHANGE = "Lamborghini";
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    private static ManufacturerDao manufacturerDao = (ManufacturerDao)
            injector.getInstance(ManufacturerDao.class);

    public static void main(String[] args) {
        Manufacturer manufacturerFerrari = new Manufacturer(NAME_OF_FIRST_MANUFACTURER,
                COUNTRY_OF_FIRST_MANUFACTURER);
        Manufacturer manufacturerMercedes = new Manufacturer(NAME_OF_SECOND_MANUFACTURER,
                COUNTRY_OF_SECOND_MANUFACTURER);
        manufacturerDao.create(manufacturerFerrari);
        manufacturerDao.create(manufacturerMercedes);
        //getAll test
        System.out.println(manufacturerDao.getAll());
        //get test
        System.out.println(manufacturerDao.get(manufacturerFerrari.getId()));
        System.out.println(manufacturerDao.get(manufacturerMercedes.getId()));
        //update test
        manufacturerFerrari.setName(NAME_OF_MANUFACTURER_CHANGE);
        manufacturerDao.update(manufacturerFerrari);
        System.out.println(manufacturerDao.getAll());
        //delete test
        manufacturerDao.delete(manufacturerMercedes.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
