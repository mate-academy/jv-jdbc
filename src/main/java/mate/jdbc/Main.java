package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        //* Create
        for (int i = 0; i < 10; i++) {
            manufacturerDao.create(new Manufacturer("ZAZ_" + i, "Ukr"));
        }
        //* Read all
        for(Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer);
        }
        //* Delete
        for(Manufacturer manufacturer : manufacturerDao.getAll()) {
            if (manufacturer.getId() % 2 != 0) {
                manufacturerDao.delete(manufacturer.getId());
            }
        }
        for(Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer);
        }
        //* Update
        for(Manufacturer manufacturer : manufacturerDao.getAll()) {
            manufacturer.setName("ZAZ_" + manufacturer.getId());
            manufacturer.setCountry("Ukraine");
            manufacturerDao.update(manufacturer);
        }
        for(Manufacturer manufacturer : manufacturerDao.getAll()) {
            System.out.println(manufacturer);
        }

    }

}
