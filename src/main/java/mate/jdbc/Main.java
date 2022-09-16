package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.impl.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {

        Manufacturer manufacturer = Manufacturer.of("Hyundai", "South Korea");
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Manufacturer savedManufacturer = manufacturerDao.create(manufacturer);
        System.out.println(savedManufacturer);

    }
}
