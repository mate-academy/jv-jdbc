package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.model.Manufacturer;
import mate.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao)
                injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();

        manufacturer.setName("BMW");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);
        manufacturerDao.get(manufacturer.getId());
        manufacturer.setId(10L);
        manufacturer.setName("model 2");
        manufacturer.setCountry("Polish");
        manufacturerDao.update(manufacturer);
        manufacturerDao.delete(15L);
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
