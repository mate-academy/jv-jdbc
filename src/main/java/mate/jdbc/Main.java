package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer volvo = new Manufacturer();
        volvo.setName("Volvo");
        volvo.setCountry("Sweden");

        Manufacturer mercedes = new Manufacturer();
        mercedes.setName("Mercedes");
        mercedes.setCountry("Germany");

        manufacturerDao.create(volvo);
        manufacturerDao.create(mercedes);

        System.out.println(manufacturerDao.get(mercedes.getId()));
        mercedes.setName("Mercedes-AMG");
        manufacturerDao.update(mercedes);
        manufacturerDao.delete(volvo.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
