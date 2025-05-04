package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();

        manufacturer.setId(1L);
        manufacturer.setName("ALALA");
        manufacturer.setCountry("ALAa");

        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);

        manufacturerDao.getAll().forEach(System.out::println);
        //manufacturerDao.create(manufacturer);
        //System.out.println(manufacturerDao.get(4L));
        //manufacturerDao.update(manufacturer);
    }
}
