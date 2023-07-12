package mate.jdbc;

import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer("test", "country");
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl();
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
