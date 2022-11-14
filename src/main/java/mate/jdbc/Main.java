package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer skodaCzech = new Manufacturer();
        skodaCzech.setName("Skoda");
        skodaCzech.setCountry("Czech");

        Manufacturer manufacturer1 = manufacturerDao.create(skodaCzech);
        System.out.println(manufacturer1);

        List<Manufacturer> showAllManufacturers = manufacturerDao.getAll();
        System.out.println(showAllManufacturers);
    }
}
