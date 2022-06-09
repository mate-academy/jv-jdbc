package mate.jdbc;

import mate.jdbc.lib.Injector;
import mate.jdbc.taxiservice.dao.ManufacturerDao;
import mate.jdbc.taxiservice.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.taxiservice");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("VOLVO");
        manufacturer.setCountry("Germany");

        manufacturerDao.create(manufacturer);

        manufacturer.setCountry("Sweden");
        manufacturerDao.update(manufacturer);

        System.out.println(manufacturerDao.get(manufacturer.getId()));
        System.out.println(manufacturerDao.get(77L));

        manufacturerDao.delete(manufacturer.getId());

        System.out.println(manufacturerDao.getAll());
    }
}
