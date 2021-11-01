package mate.jdbc;

import java.util.zip.DataFormatException;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao.impl");

    public static void main(String[] args) throws DataFormatException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(4L);
        manufacturer.setName("Volkswagen ");
        manufacturer.setCountry("Germany");

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        manufacturerDao.create(manufacturer);
        manufacturerDao.get(manufacturer.getId());
        manufacturerDao.getAll();
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(manufacturer.getId());

        System.out.println(manufacturerDao.getAll());
    }
}
