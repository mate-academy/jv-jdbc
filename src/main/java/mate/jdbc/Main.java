package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Volkswagen", "Germany");
        System.out.println(manufacturerDao.create(manufacturer));
        manufacturer.setName("Toyota");
        manufacturer.setCountry("Japan");
        Long getId = 3L;
        Long deleteId = 5L;
        System.out.println(manufacturerDao.update(manufacturer));
        System.out.println(manufacturerDao.get(getId));
        System.out.println(manufacturerDao.delete(deleteId));
        System.out.println(manufacturerDao.getAll());
    }
}
