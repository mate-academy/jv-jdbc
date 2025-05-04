package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("BMW");
        manufacturer2.setCountry("Germany");
        manufacturerDao.create(manufacturer2);
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Renault");
        manufacturer1.setCountry("France");
        manufacturerDao.create(manufacturer1);
        manufacturerDao.getAll().forEach(System.out::println);
        manufacturerDao.get(2L);
        manufacturerDao.get(3L);
        manufacturer2.setCountry("Ukraine");
        manufacturerDao.update(manufacturer2);
        manufacturerDao.delete(2L);
        manufacturerDao.delete(5L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
