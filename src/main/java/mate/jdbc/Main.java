package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.Statement;

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
