package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.jdbc");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Reno");
        manufacturer.setCountry("France");
        manufacturerDao.create(manufacturer);
        System.out.println(manufacturer);
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        System.out.println(manufacturerList);
    }
}
