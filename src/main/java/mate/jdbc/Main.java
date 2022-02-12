package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Main main = new Main();
        main.manufacturerDaoInit();
    }

    private void manufacturerDaoInit() {
        ManufacturerDao manufacturerDao
                = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer exitManufacturer = manufacturerDao.create(getNewManufacturer());
        exitManufacturer.setCountry("New Germany");
        manufacturerDao.update(exitManufacturer);
        manufacturerDao.delete(1L);
        System.out.println(manufacturerDao.gerAll());
    }

    private Manufacturer getNewManufacturer() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("Germany");
        manufacturer.setName("Audi");
        return manufacturer;
    }
}
