package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = null;
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        manufacturer = new Manufacturer("Kia", "Korea");
        manufacturerDao.create(manufacturer);
        manufacturer = new Manufacturer("Porsche", "Germany");
        manufacturerDao.create(manufacturer);
        manufacturer = new Manufacturer("Tesla", "USA");
        manufacturerDao.create(manufacturer);
        manufacturer = new Manufacturer("Toyota", "Japan");
        manufacturerDao.create(manufacturer);
        manufacturerDao.getAll().forEach(m -> System.out.println("manufacturer" + m.toString()));
        manufacturerDao.delete(18L);
        manufacturerDao.getAll().forEach(m -> System.out.println("manufacturer" + m.toString()));
        System.out.println(manufacturerDao.get(22L).get().toString());
        manufacturerDao.update(new Manufacturer(24L, "Audi", "Germany"));
        manufacturerDao.getAll().forEach(m -> System.out.println("manufacturer" + m.toString()));
    }
}
