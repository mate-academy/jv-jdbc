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
        manufacturer.setCountry("Germany");
        manufacturer.setName("BMW");

        Manufacturer updateManufacturer = new Manufacturer();
        updateManufacturer.setCountry("England");
        updateManufacturer.setName("Rolls");
        updateManufacturer.setId(3L);

        System.out.println(manufacturerDao.update(updateManufacturer));

        //System.out.println(manufacturerDao.create(manufacturer));
        //System.out.println(manufacturerDao.delete(4L));
        System.out.println(manufacturerDao.getAll());
        //System.out.println(manufacturerDao.get(1L));
    }
}
