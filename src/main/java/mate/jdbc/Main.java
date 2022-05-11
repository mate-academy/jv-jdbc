package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("FirstManufacturer");
        manufacturer1.setCountry("Ukraine");
        manufacturerDao.create(manufacturer1);
        manufacturer1.setName("ModifiedFirstManufacturer");
        System.out.println(manufacturerDao.get(manufacturerDao.update(manufacturer1).getId()));
        manufacturerDao.delete(manufacturerDao.update(manufacturer1).getId());
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
