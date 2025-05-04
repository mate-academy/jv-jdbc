package mate.jdbc;

import mate.jdbc.lib.Injector;
import mate.jdbc.manufacturer.ManufacturerDao;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("com.mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Manufacturer");
        manufacturer.setCountry("Country");

    }
}
