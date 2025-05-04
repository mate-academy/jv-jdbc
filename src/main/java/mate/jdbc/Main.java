package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Honda");
        manufacturer.setCountry("Japan");
        manufacturer = manufacturerDao.create(manufacturer);
        System.out.println(manufacturer);
        System.out.println(manufacturerDao.get(manufacturer.getId()));
        manufacturer.setName("Renault");
        manufacturer.setCountry("France");
        System.out.println(manufacturerDao.update(manufacturer));
        System.out.println(manufacturerDao.delete(manufacturer.getId()));
        System.out.println(manufacturerDao.getAll());
    }
}
