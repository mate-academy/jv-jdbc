package mate.jdbc;

import mate.jdbc.dao.ManufacturersDao;
import mate.jdbc.dao.ManufacturersDaoImpl;
import mate.jdbc.models.Manufacturer;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ManufacturersDao manufacturersDao = new ManufacturersDaoImpl();
        List<Manufacturer> all = manufacturersDao.getAll();
        for (Manufacturer manufacturer : all) {
            System.out.println(manufacturer);
        }
    }
}
