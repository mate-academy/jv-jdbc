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
        System.out.println(manufacturerDao.create(manufacturer));
        System.out.println("------------------------------");
        System.out.println(manufacturerDao.get(2L));
        System.out.println("------------------------------");
        manufacturer.setName("Renault");
        manufacturer.setCountry("France");
        manufacturer.setId(3L);
        System.out.println(manufacturerDao.update(manufacturer));
        System.out.println("------------------------------");
        System.out.println(manufacturerDao.delete(5L));
        System.out.println("------------------------------");
        System.out.println(manufacturerDao.getAll());
    }
}
