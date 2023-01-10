package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Mercedes");
        manufacturer.setCountry("Germany");
        manufacturerDao.create(manufacturer);

        System.out.println(manufacturerDao.get(1L));

        System.out.println(manufacturerDao.getAll());

        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setId(1L);
        manufacturer1.setName("DDDT");
        manufacturer1.setCountry("Ukraine");
        manufacturerDao.update(manufacturer1);

        manufacturerDao.delete(1L);
    }
}
