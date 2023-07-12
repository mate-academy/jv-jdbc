package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer1 = manufacturerDao
                .create(new Manufacturer("Name123", "Country123"));
        manufacturerDao.delete(3L);
        manufacturerDao.get(Long.valueOf(1));
        manufacturer1.setName("NewName");
        manufacturerDao.update(manufacturer1);
        System.out.println(manufacturerDao.getAll());
    }
}
