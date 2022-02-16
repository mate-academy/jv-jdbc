package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Manufacturer manufacturer = new Manufacturer(3L, "Alex", "Poland");
        Manufacturer updatedManufacturer = manufacturerDao.update(manufacturer);

    }
}
