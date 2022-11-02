package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao.impl");

    public static void main(String[] args) {

        ManufacturerDao manufacturersDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        /*
        List<Manufacturer> allManufacturers = manufacturersDao.getAll();
        for (Manufacturer m : allManufacturers) {
            System.out.println(m);
        }

        boolean delete = manufacturersDao.delete(allManufacturers.get(0).getId());

        manufacturersDao.update(new Manufacturer(2L, "Lambo", "Italy"));



        manufacturersDao.create(new Manufacturer("Audi", "Germany"));


         */
        System.out.println(manufacturersDao.get(2L));


    }
}
