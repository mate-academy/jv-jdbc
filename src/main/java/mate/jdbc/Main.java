package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("Ukraine");
        manufacturer.setName("Stepan");
        manufacturerDao.create(manufacturer);

        manufacturerDao.get(2L);

        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Bill");
        manufacturer1.setCountry("USA");
        manufacturerDao.create(manufacturer1);

        manufacturer1.setCountry("France");
        manufacturerDao.update(manufacturer1);

        manufacturerDao.delete(3L);

        manufacturerDao.getAll().forEach(System.out::println);
    }
}
