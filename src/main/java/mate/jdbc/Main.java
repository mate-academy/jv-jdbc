package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("BMW");
        manufacturer.setCountry("Germany");
        System.out.println(manufacturerDao.create(manufacturer));
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Renault");
        manufacturer1.setCountry("France");
        System.out.println(manufacturerDao.create(manufacturer1));
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println(manufacturerDao.get(1L));
        System.out.println(manufacturerDao.get(3L));
        manufacturer.setCountry("Ukraine");
        System.out.println(manufacturerDao.update(manufacturer));
        manufacturerDao.delete(2L);
        manufacturerDao.delete(5L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
