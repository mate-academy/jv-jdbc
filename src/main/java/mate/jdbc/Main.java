package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer1 = new Manufacturer();
        System.out.println(manufacturerDao.get(3L));
        manufacturer1.setName("Anton");
        manufacturer1.setCountry("Ukraine");
        manufacturerDao.create(manufacturer1);
        System.out.println(manufacturerDao.get(1L));
        for (Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer);
        }
        manufacturer1.setId(3L);
        manufacturerDao.create(manufacturer1);
        System.out.println(manufacturerDao.get(2L));
        manufacturerDao.delete(22L);
        System.out.println(manufacturerDao.get(9L));
    }
}
