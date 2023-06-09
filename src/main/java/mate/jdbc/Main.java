package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Long get_index = 3L;
        Long delete_index = 5L;
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Volkswagen", "Germany");
        System.out.println(manufacturerDao.create(manufacturer));
        manufacturer.setName("Toyota");
        manufacturer.setCountry("Japan");
        System.out.println(manufacturerDao.update(manufacturer));
        System.out.println(manufacturerDao.get(get_index));
        System.out.println(manufacturerDao.delete(delete_index));
        System.out.println(manufacturerDao.getAll());
    }
}
