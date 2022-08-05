package mate.jdbc;

import mate.jdbc.lib.ManufacturerDao;
import mate.jdbc.lib.ManufacturerDaoImpl;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Manufacturer> manufacturers = new ArrayList<>();
        ConnectionUtil connectionUtil = new ConnectionUtil();
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        manufacturers = manufacturerDao.getAll();
        System.out.println(manufacturers.toString());

        //Injector injector = Injector.getInstance("mate.jdbc");
        //ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
    }
}
