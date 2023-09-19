package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer firstManufacturer = new Manufacturer("BMV","Germany");
        Manufacturer secondManufacturer = new Manufacturer("Rolls-Royce","England");
        Manufacturer thirdManufacturer = new Manufacturer("Porsche","Germany");
        manufacturerDao.create(firstManufacturer);
        manufacturerDao.create(secondManufacturer);
        manufacturerDao.create(thirdManufacturer);
        manufacturerDao.get(firstManufacturer.getId());
        firstManufacturer.setCountry("Ukraine");
        manufacturerDao.update(firstManufacturer);
        manufacturerDao.delete(firstManufacturer.getId());
        manufacturerDao.getAll();
    }
}
