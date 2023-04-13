package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();
        Optional <Manufacturer> manufacturer = manufacturerDao.get(3L);
        System.out.println(manufacturer.isPresent());
        System.out.println(manufacturer);
    }
}
