package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao dao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("China");
        manufacturer.setName("silk");
        System.out.println(dao.create(manufacturer));
        System.out.println(dao.get(2L));
        System.out.println(dao.update(new Manufacturer(9L, "gold", "South Africa")));
        System.out.println(dao.delete(2L));
        System.out.println(dao.getAll());

    }
}
