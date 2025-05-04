package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer toyota = new Manufacturer("TOYOTA","Japan");
        manufacturerDao.create(toyota);

        Manufacturer ford = new Manufacturer("FORD","USA");
        manufacturerDao.create(ford);

        Manufacturer renault = new Manufacturer("RENAULT","France");
        manufacturerDao.create(renault);

        Manufacturer bmw = new Manufacturer("BMW","Germany");
        manufacturerDao.create(bmw);

        manufacturerDao.getAll();

        manufacturerDao.get(121L);

        manufacturerDao.delete(121L);

        manufacturerDao.getAll();
    }
}
