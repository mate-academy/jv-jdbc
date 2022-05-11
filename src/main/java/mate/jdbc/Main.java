package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.init.ConnectionUtil;
import mate.jdbc.model.Manufacturer;
import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        //Manufacturer manufacturer = new Manufacturer();
        //manufacturer.setName("Renault");
        //manufacturer.setCountry("France");
        //manufacturerDao.create(manufacturer);
        //System.out.println(manufacturerDao.get(2l));
        //System.out.println();
        //List<Manufacturer> all = manufacturerDao.getAll();
        //for (Manufacturer request: all) {
        //    System.out.println(request);
        //}
        //System.out.println();
        //manufacturerDao.delete(3l);
        //all = manufacturerDao.getAll();
        //for (Manufacturer request: all) {
        //    System.out.println(request);


        }
    }
}
