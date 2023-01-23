package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("a");
        manufacturer.setCountry("Ukraine");
        manufacturerDao.create(manufacturer);

        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("b");
        manufacturer1.setCountry("Canada");
        manufacturerDao.create(manufacturer1);

        System.out.println(manufacturerDao.get(manufacturer.getId()));
        manufacturer1.setCountry("USA");
        manufacturerDao.update(manufacturer1);
        manufacturerDao.delete(manufacturer1.getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
