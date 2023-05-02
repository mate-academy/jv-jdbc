package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer(null, "toyota", "japan");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        System.out.println(manufacturerDao.create(manufacturer) + " manufacturer created");
        System.out.println(manufacturerDao.get(manufacturer.getId()));
        List<Manufacturer> manufacturers = manufacturerDao.getAll();
        for (Manufacturer m : manufacturers) {
            System.out.println(m);
        }
        manufacturer.setName("ford");
        manufacturer.setCountry("usa");
        System.out.println(manufacturerDao.update(manufacturer) + " manufacturer with id - "
                + manufacturer.getId() + " is updated");
        System.out.println("Manufacturer is deleted = "
                + manufacturerDao.delete(manufacturer.getId()));
    }
}
