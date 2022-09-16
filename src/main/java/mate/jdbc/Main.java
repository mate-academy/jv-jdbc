package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.impl.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Manufacturer manufacturer = Manufacturer.of("Toyota", "Japan");
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        manufacturerDao.get(1l).ifPresent(System.out::println);
        //System.out.println(manufacturer);
        //boolean isDeleted = manufacturerDao.delete(4l);
        //System.out.println(isDeleted);
        //List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        //allManufacturers.forEach(System.out::println);

    }
}
