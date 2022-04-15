package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer germanyManufacturer = new Manufacturer("Mercedes-Benz", "Germany");
        Manufacturer changedManufacturer = manufacturerDao.get(2L).orElseThrow();
        changedManufacturer.setCountry("France");
        manufacturerDao.update(changedManufacturer);
        for (Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer);
        }
    }
}
