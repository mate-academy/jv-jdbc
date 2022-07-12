package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.dao.ManufacturerDaoImpl;
import mate.jdbc.model.Manufacturer;

public class Main {
    public static void main(String[] args) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("dasda");
        manufacturer.setName("afeds");

        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl();

        System.out.println(manufacturerDao.get(12L));

    }
}
