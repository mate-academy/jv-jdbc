package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer(null, "Lexus", "Japan");
        manufacturerDao.create(manufacturer);
        manufacturer.setName("Porsche");
        manufacturer.setCountry("Germany");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(manufacturer.getId());

    }
}
