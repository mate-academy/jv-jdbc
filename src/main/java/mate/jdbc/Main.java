package mate.jdbc;

import mate.jdbc.dao.ManufecturersDao;
import mate.jdbc.dao.ManufecturersDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Shakil");
        manufacturer.setCountry("USA");
        ManufecturersDao manufecturersDao = new ManufecturersDaoImpl();
        Manufacturer savedManufacturer = manufecturersDao.create(manufacturer);
        System.out.println(savedManufacturer);
    }
}
