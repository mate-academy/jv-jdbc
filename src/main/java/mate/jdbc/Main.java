package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer lenovo = new Manufacturer("Lenovo", "China");

        System.out.println(manufacturerDao.create(lenovo));
        System.out.println(manufacturerDao.get(2L));
        lenovo.setCountry("Ukraine");
        System.out.println(manufacturerDao.update(lenovo));
        System.out.println(manufacturerDao.delete(4L));
        System.out.println(manufacturerDao.getAll());
    }
}
