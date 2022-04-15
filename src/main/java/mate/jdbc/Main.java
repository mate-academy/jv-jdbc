package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static final String BOSENDORFER_MANUFACTURER = "Bösendorfer";
    public static final String BOSENDORFER_MANUFACTURER_COUNTRY = "Austria";
    public static final String SEINWAY_MANUFACTURER = "Steinway & Sons";
    public static final String SEINWAY_MANUFACTURER_COUNTRY = "USA";
    public static final String BLUTNER_MANUFACTURER = "Blüthner";
    public static final String BLUTNER_MANUFACTURER_COUNTRY = "GERMANY";
    public static final String BLUTNER_MANUFACTURER_NEW = "Blüthner & B";

    public static void main(String[] args) {
        Manufacturer bosendorferManufacturer = new Manufacturer();
        bosendorferManufacturer.setCountry(BOSENDORFER_MANUFACTURER_COUNTRY);
        bosendorferManufacturer.setName(BOSENDORFER_MANUFACTURER);

        Manufacturer steinwayManufacturer = new Manufacturer();
        steinwayManufacturer.setCountry(SEINWAY_MANUFACTURER_COUNTRY);
        steinwayManufacturer.setName(SEINWAY_MANUFACTURER);

        Manufacturer bluthnerManufacturer = new Manufacturer();
        bluthnerManufacturer.setCountry(BLUTNER_MANUFACTURER_COUNTRY);
        bluthnerManufacturer.setName(BLUTNER_MANUFACTURER);

        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        System.out.println(manufacturerDao.create(bosendorferManufacturer));
        System.out.println(manufacturerDao.create(steinwayManufacturer));
        System.out.println(manufacturerDao.create(bluthnerManufacturer));
        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(2L));
        System.out.println(manufacturerDao.get(3L));
        bluthnerManufacturer.setName(BLUTNER_MANUFACTURER_NEW);
        System.out.println(manufacturerDao.update(bluthnerManufacturer));
        System.out.println(manufacturerDao.delete(3L));
        List<Manufacturer> all = manufacturerDao.getAll();
        System.out.println(all);
    }
}
