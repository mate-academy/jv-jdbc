package mate.jdbc;

import java.util.List;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();

        List<Manufacturer> allManufacturers = manufacturerDao.getAll();
        allManufacturers.forEach(System.out::println);

        System.out.println(manufacturerDao.get(2L));

        Manufacturer getManufacturer =
                manufacturerDao.create(new Manufacturer("Kia", "South Korea"));
        System.out.println(getManufacturer);

        manufacturerDao.delete(3L);

        manufacturerDao.update(new Manufacturer(2L,"Tesla", "USA"));
    }
}
