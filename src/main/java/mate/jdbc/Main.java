package mate.jdbc;

import mate.jdbc.dao.ManufecturersDao;
import mate.jdbc.dao.ManufecturersDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Cobe");
        manufacturer.setCountry("Morocco");
        ManufecturersDao manufecturersDao = new ManufecturersDaoImpl();
        System.out.println(manufecturersDao.get(1L));
    }
}
