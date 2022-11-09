package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector
                .getInstance(ManufacturerDao.class);
        Manufacturer manufacturerKia = new Manufacturer(null,"Kia","South Korea");
        manufacturerDao.create(manufacturerKia);
        System.out.println(manufacturerDao.getAll());
        manufacturerKia.setCountry("France");
        manufacturerKia.setCompanyName("notKia");
        manufacturerDao.update(manufacturerKia);
        System.out.println(manufacturerDao.getAll());
        manufacturerDao.delete(manufacturerKia.getId());
        System.out.println(manufacturerDao.getAll());
    }
}
