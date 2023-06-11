package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        manufacturerDao.hardReset();

        Manufacturer bmw = new Manufacturer("BMW", "Germany");
        Manufacturer toyota = new Manufacturer("Toyota", "Japan");

        final Long bmwId = manufacturerDao.create(bmw).getId();
        final Long toyotaId = manufacturerDao.create(toyota).getId();

        System.out.println(manufacturerDao.getAll());

        Manufacturer lexus = new Manufacturer("Lexus", "Japan", toyotaId);
        manufacturerDao.update(lexus);
        System.out.println(manufacturerDao.get(toyotaId).get());

        manufacturerDao.delete(bmwId);
        System.out.println(manufacturerDao.getAll());
    }
}
